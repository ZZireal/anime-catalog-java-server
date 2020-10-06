package by.bsuir.animeCatalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "role")
public class Role implements Serializable {

    @Id
    private String _id;

    @Indexed
    private String title;
    private String description;

    public Role() {}

    public Role(String title, String description) {
        this.title = title;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "{ \"_id\":\"%s\", \"title\":\"%s\", \"description\":\"%s\" }",
                _id, title, description);
    }
}
