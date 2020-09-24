package by.bsuir.animeCatalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "user")
public class User implements Serializable {

    @Id
    private String id;

    @Indexed
    private String username;
    private String firstname;
    private String lastname;
    private String passwordHash;
    private String roleId;

    public User() {}

    public User(String username, String firstname, String lastname, String passwordHash, String roleId) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return String.format(
                "{ \"id\":\"%s\", \"username\":\"%s\", \"firstname\":\"%s\", \"lastname\":\"%s\", \"passwordHash\":%s, \"roleId\":%s }",
                id, username, firstname, lastname, passwordHash, roleId);
    }
}
