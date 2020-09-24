package by.bsuir.animeCatalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "anime")
public class Anime implements Serializable {

    @Id
    private String _id;

    @Indexed
    private String title;
    private String category;
    private String genre;
    private int series;
    private int year;
    private int age;
    private String image;

    public Anime() {}

    public Anime(String title, String category, String genre, int series, int year, int age, String image) {
        this.title = title;
        this.category = category;
        this.genre = genre;
        this.series = series;
        this.year = year;
        this.age = age;
        this.image = image;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format(
                "{ \"_id\":\"%s\", \"title\":\"%s\", \"category\":\"%s\", \"genre\":\"%s\", \"series\":%s, \"year\":%s, \"age\":%s, \"image\":\"%s\" }",
                _id, title, category, genre, series, year, age, image);
    }
}