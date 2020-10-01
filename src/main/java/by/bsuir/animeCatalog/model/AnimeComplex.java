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
    private String _id;

    @Indexed
    private String title;
    private List<Anime> anime;

    public AnimeComplex() {}

    public AnimeComplex(String title, List<Anime> anime) {
        this.title = title;
        this.anime = anime;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Anime> getAnime() {
        return anime;
    }

    public void setAnime(List<Anime> anime) {
        this.anime = anime;
    }

    @Override
    public String toString() {
        return String.format(
                "{ \"_id\":\"%s\", \"title\":\"%s\", \"anime\":\"%s\" }",
                _id, title, anime.toString());
    }
}
