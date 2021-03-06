package by.bsuir.animeCatalog.controller;

import by.bsuir.animeCatalog.model.Role;
import by.bsuir.animeCatalog.repositories.RoleRepository;
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
@RequestMapping("role")
public class RoleController implements WebMvcConfigurer {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @GetMapping("/")
    public ResponseEntity<?> getRole (
            @RequestParam(required = false, name = "id", defaultValue = "noId") String id,
            @RequestParam(required = false, name = "field", defaultValue = "noField") String field,
            @RequestParam(required = false, name = "order", defaultValue = "noOrder") String order
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        if ( !id.equals("noId") ) {
            return new ResponseEntity(roleRepository.findBy_id(id), httpHeaders, HttpStatus.OK);
        }

        if ( !field.equals("noField") || !order.equals("noOrder")) {
            Sort.Direction sort = Sort.Direction.ASC;
            if (order.equals("false")) sort = Sort.Direction.DESC;
            return new ResponseEntity(roleRepository.findAll(Sort.by(sort, field)), httpHeaders, HttpStatus.OK);
        }
        return new ResponseEntity(roleRepository.findAll(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createRole (
            @RequestParam(required = false, name = "id", defaultValue = "noId") String id,
            @RequestBody Role role
    ) {
        //In fact, it is @DeleteMapping
        if (!id.equals("noId")) {
            Role roleToDelete = roleRepository.findBy_id(id);
            roleRepository.delete(roleToDelete);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity(roleToDelete, httpHeaders, HttpStatus.OK);
        }
        //

        roleRepository.save(role);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccessControlAllowMethods(List.of(HttpMethod.POST));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(role, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<?>  updateRole (@RequestBody Role roleNew) {
        Role roleOld = roleRepository.findBy_id(roleNew.get_id());

        roleOld.setTitle(roleNew.getTitle());
        roleOld.setDescription(roleNew.getDescription());

        roleRepository.save(roleOld);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity(roleOld, httpHeaders, HttpStatus.OK);
    }
}
