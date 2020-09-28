package by.bsuir.animeCatalog.controller;

import by.bsuir.animeCatalog.model.Anime;
import by.bsuir.animeCatalog.model.AnimeComplex;
import by.bsuir.animeCatalog.repositories.AnimeComplexRepository;
import by.bsuir.animeCatalog.repositories.AnimeRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> getAnimeComplex () {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(animeComplexRepository.findAll(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createAnimeComplex (
            @RequestParam(required = false, name = "id", defaultValue = "noId") String id,
            @RequestBody AnimeComplex animeComplex
    ) {
        //In fact, it is @DeleteMapping
        if (!id.equals("noId")) {
            AnimeComplex animeComplexToDelete = animeComplexRepository.findBy_id(id);
            animeComplexRepository.delete(animeComplexToDelete);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity(animeComplexToDelete, httpHeaders, HttpStatus.OK);
        }
        //
        animeComplexRepository.save(animeComplex);

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

    @PutMapping("/")
    public ResponseEntity<?>  updateAnimeComplex (@RequestBody AnimeComplex animeComplexNew) {
        AnimeComplex animeComplexOld = animeComplexRepository.findByTitle(animeComplexNew.getTitle());

        animeComplexOld.setAnimeId(animeComplexNew.getAnimeId());

        animeComplexRepository.save(animeComplexOld);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(animeComplexOld, httpHeaders, HttpStatus.OK);
    }
}
