package com.passgenius.serviceauth.service;


import com.passgenius.serviceauth.exceptions.IncorrectPassowrdrException;
import com.passgenius.serviceauth.exceptions.InvalidAuthorizationHeaderException;
import com.passgenius.serviceauth.exceptions.UserAlreadyExistException;
import com.passgenius.serviceauth.exceptions.UserNotFoundException;
import com.passgenius.serviceauth.models.User2;
import com.passgenius.serviceauth.repository.UserRepository;
import com.passgenius.serviceauth.utils.JwtUtil;
import com.passgenius.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void registerUser(User2 user2, String authorizationHeader)
            throws UserAlreadyExistException, InvalidAuthorizationHeaderException {
        String password = extractPassword(authorizationHeader);
        var existingUser = userRepository.findByUsername(user2.getUsername()).orElse(null);
        if (existingUser == null) {
            user2.setId(UUID.randomUUID().toString());
            user2.setHashedPassword(passwordEncoder.hashPassword(password));
            userRepository.save(user2);
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
