package by.bsuir.animeCatalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "user")
public class User implements Serializable {

    @Id
    private String _id;

    @Indexed
    private String username;
    private String firstname;
    private String lastname;
    private String passwordHash;
    private String role;
    private List<String> favourite;

    public User() {}

    public User(String username, String firstname, String lastname, String passwordHash, String role, List<String> favourite) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.passwordHash = passwordHash;
        this.role = role;
        this.favourite = favourite;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<String> getFavourite() {
        return favourite;
    }

    public void setFavourite(List<String> favourite) {
        this.favourite = favourite;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format(
                "{ \"_id\":\"%s\", \"username\":\"%s\", \"firstname\":\"%s\", \"lastname\":\"%s\", \"passwordHash\":%s, \"role\":%s, \"favourite\":%s  }",
                _id, username, firstname, lastname, passwordHash, role, favourite);
    }
}
