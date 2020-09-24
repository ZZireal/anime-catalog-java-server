package by.bsuir.animeCatalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Document(collection = "anime-complex")
public class AnimeComplex implements Serializable {

    @Id
    private String id;

    @Indexed
    private String title;
    private List<String> animeId;

    public AnimeComplex() {}

    public AnimeComplex(String title, List<String> animeId) {
        this.title = title;
        this.animeId = animeId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAnimeId() {
        return animeId;
    }

    public void setAnimeId(List<String> anime) {
        this.animeId = anime;
    }

    @Override
    public String toString() {
        return String.format(
                "{ \"_id\":\"%s\", \"title\":\"%s\", \"anime\":\"%s\" }",
                id, title, animeId.toString());
    }
}
