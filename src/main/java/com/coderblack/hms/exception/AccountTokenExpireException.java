package com.coderblack.hms.exception;

public class AccountTokenExpireException extends RuntimeException {
    public AccountTokenExpireException(String msg) {
        super(msg);
    }
}
