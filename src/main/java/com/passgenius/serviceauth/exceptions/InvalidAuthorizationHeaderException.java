package com.passgenius.serviceauth.exceptions;

public class InvalidAuthorizationHeaderException extends Exception{

    public InvalidAuthorizationHeaderException(String message){
        super(message);
    }
}
