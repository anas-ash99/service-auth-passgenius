package com.passgenius.serviceauth.controller;



import com.passgenius.serviceauth.exceptions.IncorrectPassowrdrException;
import com.passgenius.serviceauth.exceptions.InvalidAuthorizationHeaderException;
import com.passgenius.serviceauth.exceptions.UserAlreadyExistException;
import com.passgenius.serviceauth.exceptions.UserNotFoundException;
import com.passgenius.serviceauth.models.User;
import com.passgenius.serviceauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody User user,
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        try {
            userService.registerUser(user, authorizationHeader);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body("User already exist");
        } catch (InvalidAuthorizationHeaderException e) {
            return ResponseEntity.badRequest().body("Invalid authorization header!");
        }

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            var user = userService.loginUser(authorizationHeader);
            return ResponseEntity.ok(user);
        } catch (InvalidAuthorizationHeaderException e) {
            return ResponseEntity.badRequest().body("Invalid authorization header");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User doesn't exist!");
        } catch (IncorrectPassowrdrException e) {
            return ResponseEntity.badRequest().body("Incorrect password!");
        }
    }
}

