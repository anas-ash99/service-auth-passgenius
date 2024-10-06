package com.passgenius.serviceauth.service;


import com.passgenius.serviceauth.exceptions.IncorrectPassowrdrException;
import com.passgenius.serviceauth.exceptions.InvalidAuthorizationHeaderException;
import com.passgenius.serviceauth.exceptions.UserAlreadyExistException;
import com.passgenius.serviceauth.exceptions.UserNotFoundException;
import com.passgenius.serviceauth.models.User;
import com.passgenius.serviceauth.repository.UserRepository;
import com.passgenius.serviceauth.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import static com.passgenius.serviceauth.utils.StringUtils.extractCredentials;
import static com.passgenius.serviceauth.utils.StringUtils.extractPassword;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordService passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    public void registerUser(User user, String authorizationHeader)
            throws UserAlreadyExistException, InvalidAuthorizationHeaderException {
        String password = extractPassword(authorizationHeader);
        var existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (existingUser == null) {
            user.setId(UUID.randomUUID().toString());
            user.setHashedPassword(passwordEncoder.hashPassword(password));
            user.setCreatedAt(LocalDateTime.now().toString());
            userRepository.save(user);
        } else {
            logger.error("User already exist");
            throw new UserAlreadyExistException("User already exist");
        }
    }

    public User loginUser(String authorizationHeader) throws InvalidAuthorizationHeaderException, UserNotFoundException, IncorrectPassowrdrException {
        String[] extractedCredentials = extractCredentials(authorizationHeader);
        var username = extractedCredentials[0];
        var password = extractedCredentials[1];
        var existingUser = userRepository.findByUsername(username).orElse(null);
        if (existingUser == null){
            throw new UserNotFoundException("");
        }
        if (passwordEncoder.verifyPassword(password, existingUser.getHashedPassword())){
            final String jwt = jwtUtil.generateToken(username);
            existingUser.setToken(jwt);
            return existingUser;
        }else {
            throw new IncorrectPassowrdrException("");
        }

    }
}
