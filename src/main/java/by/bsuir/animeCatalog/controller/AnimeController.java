package by.bsuir.animeCatalog.controller;

import by.bsuir.animeCatalog.model.Anime;
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
@RequestMapping("anime")
public class AnimeController implements WebMvcConfigurer {
    @Autowired
    private AnimeRepository animeRepository;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @GetMapping("/")
    public ResponseEntity<?> getAnime (
            @RequestParam(required = false, name = "id", defaultValue = "noId") String id,
            @RequestParam(required = false, name = "field", defaultValue = "noField") String field,
            @RequestParam(required = false, name = "order", defaultValue = "noOrder") String order
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        if ( !id.equals("noId") ) {
            return new ResponseEntity(animeRepository.findBy_id(id), httpHeaders, HttpStatus.OK);
        }

        if ( !field.equals("noField") || !order.equals("noOrder")) {
            Sort.Direction sort = Sort.Direction.ASC;
            if (order.equals("false")) sort = Sort.Direction.DESC;
            return new ResponseEntity(animeRepository.findAll(Sort.by(sort, field)), httpHeaders, HttpStatus.OK);
        }
        return new ResponseEntity(animeRepository.findAll(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createAnime (
            @RequestParam(required = false, name = "id", defaultValue = "noId") String id,
            @RequestBody Anime anime
    ) {
        //In fact, it is @DeleteMapping
        if (!id.equals("noId")) {
            Anime animeToDelete = animeRepository.findBy_id(id);
            animeRepository.delete(animeToDelete);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity(animeToDelete, httpHeaders, HttpStatus.OK);
        }
        //

        animeRepository.save(anime);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowMethods(List.of(HttpMethod.POST));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(anime, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<?>  updateAnime (@RequestBody Anime animeNew) {
        Anime animeOld = animeRepository.findBy_id(animeNew.get_id());

        animeOld.setTitle(animeNew.getTitle());
        animeOld.setCategory(animeNew.getCategory());
        animeOld.setGenre(animeNew.getGenre());
        animeOld.setSeries(animeNew.getSeries());
        animeOld.setYear(animeNew.getYear());
        animeOld.setAge(animeNew.getAge());

        animeRepository.save(animeOld);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(animeOld, httpHeaders, HttpStatus.OK);
    }
}
