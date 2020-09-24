package by.bsuir.animeCatalog.repositories;

import by.bsuir.animeCatalog.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role, String> {

    @Query("{ 'title' : {$regex: ?0, $options: 'i' }}")
    public Role findByTitle(String title);
}