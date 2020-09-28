package by.bsuir.animeCatalog.repositories;

import by.bsuir.animeCatalog.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RoleRepository extends MongoRepository<Role, String> {
    public Role findBy_id(String id);

    @Query("{ 'title' : {$regex: ?0, $options: 'i' }}")
    public Role findByTitle(String title);
}