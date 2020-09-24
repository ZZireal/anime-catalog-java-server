package by.bsuir.animeCatalog.controller;

import by.bsuir.animeCatalog.model.Anime;
import by.bsuir.animeCatalog.repositories.AnimeRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
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
        registry.addMapping("/**").allowedOrigins("*");
    }

    @GetMapping("/")
    public ResponseEntity<?> getAnime (
            @RequestParam(required = false, name = "id", defaultValue = "noId") String id,
            @RequestParam(required = false, name = "field", defaultValue = "noField") String field,
            @RequestParam(required = false, name = "order", defaultValue = "noOrder") String order
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(id + field + order);

        if ( !id.equals("noId") ) {
            System.out.println(animeRepository.findBy_id(id));
            return new ResponseEntity(animeRepository.findBy_id(id), httpHeaders, HttpStatus.OK);
        }

        if ( !field.equals("noField") || !order.equals("noOrder")) {
            Sort.Direction sort = Sort.Direction.ASC;
            if (order.equals("false")) sort = Sort.Direction.DESC;
            return new ResponseEntity(animeRepository.findAll(Sort.by(sort, field)), httpHeaders, HttpStatus.OK);
        }
        System.out.println("Try get all!");
        return new ResponseEntity(animeRepository.findAll(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createAnime (@RequestBody Anime anime) {
        animeRepository.save(anime);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowMethods(List.of(HttpMethod.POST));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(anime, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?>  deleteAnime (@RequestParam(name = "id") String id) {
        Anime anime = animeRepository.findBy_id(id);
        animeRepository.delete(anime);

        HttpHeaders httpHeaders = new HttpHeaders();
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
