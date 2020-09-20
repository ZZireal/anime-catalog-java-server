package by.bsuir.animeCatalog;

import by.bsuir.animeCatalog.model.Anime;
import by.bsuir.animeCatalog.repositories.AnimeRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@ResponseBody
@JsonSerialize
@RequestMapping("anime")
public class AnimeCatalogApplication implements CommandLineRunner {
	@Autowired
	private AnimeRepository animeRepository;
	@Autowired
	private MongoTemplate template;
	@Autowired
	private MongoConverter mongoConverter;

	public static void main(String[] args) {
		SpringApplication.run(AnimeCatalogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception { }

	@GetMapping("/")
	public ResponseEntity<?> getAnime () {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccessControlAllowOrigin("*");

		return new ResponseEntity(animeRepository.findAll(), httpHeaders, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<?> createAnime (@RequestBody Anime anime) {
		System.out.println(anime);
		animeRepository.save(anime);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccessControlAllowOrigin("*");
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity(anime, httpHeaders, HttpStatus.OK);
	}

	@DeleteMapping("/")
	public ResponseEntity<?>  deleteAnime (@RequestBody String title) {
		Anime anime = animeRepository.findByTitle(title);
		animeRepository.delete(anime);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccessControlAllowOrigin("*");

		return new ResponseEntity(anime, httpHeaders, HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<?>  updateAnime (@RequestBody Anime animeNew) {
		String image = animeRepository.findByTitle(animeNew.getTitle()).getImage();

		Anime animeOld = animeRepository.findByTitle(animeNew.getTitle());

		animeOld.setImage(image);

		animeOld.setCategory(animeNew.getCategory());
		animeOld.setGenre(animeNew.getGenre());
		animeOld.setSeries(animeNew.getSeries());
		animeOld.setYear(animeNew.getYear());
		animeOld.setAge(animeNew.getAge());

		animeRepository.save(animeOld);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccessControlAllowOrigin("*");

		return new ResponseEntity(animeOld, httpHeaders, HttpStatus.OK);
	}
}