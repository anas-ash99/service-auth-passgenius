package com.passgenius.serviceauth.utils;

import com.passgenius.serviceauth.exceptions.InvalidAuthorizationHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class StringUtils {
    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
    public static String extractPassword(String authorizationHeader) throws InvalidAuthorizationHeaderException {
        // Example: Decode the Basic Auth header
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2); // username:password
            return values.length > 1 ? values[1] : null;
        }
        logger.error("Error extracting password");
        throw new InvalidAuthorizationHeaderException("Error extracting password");
    }

    public static String[] extractCredentials(String authorizationHeader) throws InvalidAuthorizationHeaderException {
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic")) {
            // Remove "Basic " prefix and decode the Base64 encoded string
            String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedBytes, StandardCharsets.UTF_8);

            // Split credentials into username and password
            return credentials.split(":", 2); // returns an array with [0] = username, [1] = password
        }
        throw new InvalidAuthorizationHeaderException("Invalid Authorization header");
    }
}
