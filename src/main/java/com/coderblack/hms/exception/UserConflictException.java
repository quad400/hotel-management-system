package com.coderblack.hms.exception;


public class UserConflictException extends RuntimeException {

    public UserConflictException(String message){
        super(message);
    }
}
