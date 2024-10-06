package com.passgenius.serviceauth.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "users")
public class User {
    @Id
    private String id = UUID.randomUUID().toString();
    private String email = "";
    private String username = "";

    private String hashedPassword = "";

    private String name = "";

    private String token = "";
    private String createdAt = LocalDateTime.now().toString();


    public User(String id, String email, String username, String hashedPassword, String name, String token, String createdAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.name = name;
        this.token = token;
        this.createdAt = createdAt;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
