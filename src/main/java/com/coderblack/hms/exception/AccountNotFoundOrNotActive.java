package com.coderblack.hms.exception;

public class AccountNotFoundOrNotActive extends RuntimeException {
    public AccountNotFoundOrNotActive(String msg) {
        super(msg);
    }
}
