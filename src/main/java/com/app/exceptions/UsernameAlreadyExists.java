package com.app.exceptions;

public class UsernameAlreadyExists extends RuntimeException{

    public UsernameAlreadyExists(String message){
        super(message);
    }
}
