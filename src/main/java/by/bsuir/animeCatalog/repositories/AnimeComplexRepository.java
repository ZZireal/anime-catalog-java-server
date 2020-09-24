package by.bsuir.animeCatalog.repositories;

import by.bsuir.animeCatalog.model.AnimeComplex;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AnimeComplexRepository extends MongoRepository<AnimeComplex, String> {

    @Query("{ 'title' : {$regex: ?0, $options: 'i' }}")
    public AnimeComplex findByTitle(String title);
}