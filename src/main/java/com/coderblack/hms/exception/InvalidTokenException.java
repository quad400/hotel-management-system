package com.coderblack.hms.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String msg) {
        super(msg);
    }
}
