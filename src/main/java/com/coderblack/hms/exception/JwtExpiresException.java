package com.coderblack.hms.exception;

public class JwtExpiresException extends RuntimeException {

    public JwtExpiresException(String message){
        super(message);
    }

}
