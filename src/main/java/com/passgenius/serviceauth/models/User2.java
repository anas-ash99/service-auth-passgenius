package com.passgenius.serviceauth.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "users")
public class User2 {
    @Id
    private String id =UUID.randomUUID().toString() ;

    private String email = "";

    private String username = "";

    private String hashedPassword = "";

    private String name = "";

    private String token = "";


    public User2(){

    }
    public User2(String id, String email, String username, String hashedPassword, String name, String token) {

        this.id = id;
        this.email = email;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.name = name;
        this.token = token;
    }




    public final String getId() {
        return this.id;
    }

    public final void setId( String var1) {
        this.id = var1;
    }

    
    public final String getEmail() {
        return this.email;
    }

    public final void setEmail( String var1) {
        this.email = var1;
    }

    
    public final String getUsername() {
        return this.username;
    }

    public final void setUsername( String var1) {
        this.username = var1;
    }

    
    public final String getHashedPassword() {
        return this.hashedPassword;
    }

    public final void setHashedPassword( String var1) {
        this.hashedPassword = var1;
    }

    
    public final String getName() {
        return this.name;
    }

    public final void setName( String var1) {
        this.name = var1;
    }

    
    public final String getToken() {
        return this.token;
    }

    public final void setToken( String var1) {
        this.token = var1;
    }


    public String toString() {
        return "User(id=" + this.id + ", email=" + this.email + ", username=" + this.username + ", hashedPassword=" + this.hashedPassword + ", name=" + this.name + ", token=" + this.token + ')';
    }

    public int hashCode() {
        int result = this.id.hashCode();
        result = result * 31 + this.email.hashCode();
        result = result * 31 + this.username.hashCode();
        result = result * 31 + this.hashedPassword.hashCode();
        result = result * 31 + this.name.hashCode();
        result = result * 31 + this.token.hashCode();
        return result;
    }
}
