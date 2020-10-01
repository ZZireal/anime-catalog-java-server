package by.bsuir.animeCatalog.controller;

import by.bsuir.animeCatalog.model.Role;
import by.bsuir.animeCatalog.model.User;
import by.bsuir.animeCatalog.repositories.RoleRepository;
import by.bsuir.animeCatalog.repositories.UserRepository;
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
@RequestMapping("user")
public class UserController implements WebMvcConfigurer {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }

    @GetMapping("/")
    public ResponseEntity<?> getUser (
            @RequestParam(required = false, name = "id", defaultValue = "noId") String id,
            @RequestParam(required = false, name = "field", defaultValue = "noField") String field,
            @RequestParam(required = false, name = "order", defaultValue = "noOrder") String order
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        if ( !id.equals("noId") ) {
            return new ResponseEntity(userRepository.findBy_id(id), httpHeaders, HttpStatus.OK);
        }

        if ( !field.equals("noField") || !order.equals("noOrder")) {
            Sort.Direction sort = Sort.Direction.ASC;
            if (order.equals("false")) sort = Sort.Direction.DESC;
            return new ResponseEntity(userRepository.findAll(Sort.by(sort, field)), httpHeaders, HttpStatus.OK);
        }
        return new ResponseEntity(userRepository.findAll(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser (
            @RequestParam(required = false, name = "id", defaultValue = "noId") String id,
            @RequestBody User user
    ) {
        //In fact, it is @DeleteMapping
        if (!id.equals("noId")) {
            User userToDelete = userRepository.findBy_id(id);
            userRepository.delete(userToDelete);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity(userToDelete, httpHeaders, HttpStatus.OK);
        }
        //

        userRepository.save(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowMethods(List.of(HttpMethod.POST));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(user, httpHeaders, HttpStatus.OK);
    }

//    @DeleteMapping("/")
//    public ResponseEntity<?>  deleteUser (@RequestParam(name = "id") String id) {
//        User user = userRepository.findBy_id(id);
//        userRepository.delete(user);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        return new ResponseEntity(user, httpHeaders, HttpStatus.OK);
//    }

    @PutMapping("/")
    public ResponseEntity<?>  updateUser (@RequestBody User userNew) {
        User userOld = userRepository.findBy_id(userNew.get_id());

        userOld.setFirstname(userNew.getFirstname());
        userOld.setLastname(userNew.getLastname());
        userOld.setPasswordHash(userNew.getPasswordHash());
        userOld.setFavourite(userNew.getFavourite());
        userOld.setRole(userNew.getRole());

        userRepository.save(userOld);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(userOld, httpHeaders, HttpStatus.OK);
    }
}
