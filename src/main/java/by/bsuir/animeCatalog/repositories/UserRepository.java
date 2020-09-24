package by.bsuir.animeCatalog.repositories;

import by.bsuir.animeCatalog.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'username' : {$regex: ?0, $options: 'i' }}")
    public User findByUsername(String title);
}