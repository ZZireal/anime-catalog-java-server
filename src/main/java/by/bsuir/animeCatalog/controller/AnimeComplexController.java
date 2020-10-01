package by.bsuir.animeCatalog.controller;

import by.bsuir.animeCatalog.model.Anime;
import by.bsuir.animeCatalog.model.AnimeComplex;
import by.bsuir.animeCatalog.repositories.AnimeComplexRepository;
import by.bsuir.animeCatalog.repositories.AnimeRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RestController
@ResponseBody
@JsonSerialize
@EnableWebMvc
@RequestMapping("anime-complex")
public class AnimeComplexController implements WebMvcConfigurer {
    @Autowired
    private AnimeComplexRepository animeComplexRepository;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @GetMapping("/")
    public ResponseEntity<?> getAnimeComplex (
            @RequestParam(required = false, name = "id", defaultValue = "noId") String id,
            @RequestParam(required = false, name = "field", defaultValue = "noField") String field,
            @RequestParam(required = false, name = "order", defaultValue = "noOrder") String order
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        if ( !id.equals("noId") ) {
            return new ResponseEntity(animeComplexRepository.findBy_id(id), httpHeaders, HttpStatus.OK);
        }

        if ( !field.equals("noField") || !order.equals("noOrder")) {
            Sort.Direction sort = Sort.Direction.ASC;
            if (order.equals("false")) sort = Sort.Direction.DESC;
            return new ResponseEntity(animeComplexRepository.findAll(Sort.by(sort, field)), httpHeaders, HttpStatus.OK);
        }
        return new ResponseEntity(animeComplexRepository.findAll(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createAnimeComplex (
            @RequestParam(required = false, name = "animeComplexId", defaultValue = "noAnimeComplexId") String animeComplexId,
            @RequestParam(required = false, name = "animeId", defaultValue = "noAnimeId") String animeId,
            @RequestBody(required = false) AnimeComplex animeComplex
    ) {
        //In fact, it is @DeleteMapping
        if (!animeComplexId.equals("noAnimeComplexId") && !animeId.equals("noAnimeId")) {
            AnimeComplex animeComplexToDelete = animeComplexRepository.findBy_id(animeComplexId);
            List<Anime> animeList = animeComplexToDelete.getAnime();
            for (Anime anime : animeList) {
                if (anime.get_id().equals(animeId)) {
                    animeList.remove(anime);
                    break;
                }
            }
            animeComplexToDelete.setAnime(animeList);
            animeComplexRepository.save(animeComplexToDelete);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity(animeComplexToDelete, httpHeaders, HttpStatus.OK);
        }

        if (!animeComplexId.equals("noAnimeComplexId") && animeId.equals("noAnimeId")) {
            AnimeComplex animeComplexToDelete = animeComplexRepository.findBy_id(animeComplexId);
            animeComplexRepository.delete(animeComplexToDelete);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity(animeComplexToDelete, httpHeaders, HttpStatus.OK);
        }
        //
        animeComplexRepository.insert(animeComplex);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowMethods(List.of(HttpMethod.POST));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(animeComplex, httpHeaders, HttpStatus.OK);
    }

//    @DeleteMapping("/")
//    public ResponseEntity<?>  deleteAnimeComplex (@RequestBody String title) {
//        AnimeComplex animeComplex = animeComplexRepository.findByTitle(title);
//        animeComplexRepository.delete(animeComplex);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        return new ResponseEntity(animeComplex, httpHeaders, HttpStatus.OK);
//    }

    @PostMapping("/update")
    public ResponseEntity<?>  updateAnimeComplex (@RequestBody AnimeComplex animeComplexNew) {
        AnimeComplex animeComplexOld = animeComplexRepository.findBy_id(animeComplexNew.get_id());
        animeComplexOld.setAnime(animeComplexNew.getAnime());

        animeComplexRepository.save(animeComplexOld);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(animeComplexOld, httpHeaders, HttpStatus.OK);
    }
}
