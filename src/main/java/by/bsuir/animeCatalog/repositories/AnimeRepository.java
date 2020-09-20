package by.bsuir.animeCatalog.repositories;

import by.bsuir.animeCatalog.model.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AnimeRepository extends MongoRepository<Anime, String> {
    @Query("{ 'title' : {$regex: ?0, $options: 'i' }}")
    public Anime findByTitle(String title);
}