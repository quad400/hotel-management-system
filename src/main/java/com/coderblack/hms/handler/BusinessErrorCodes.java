package com.coderblack.hms.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessErrorCodes {

    UNIQUE_DATA(301, CONFLICT, "Entity already exist in the database"),
    BAD_REQUEST_DATA(302, BAD_REQUEST, "Invalid data or response"),
    UNAUTHORIZED_USER(303, UNAUTHORIZED, "User not Authorized"),
    NOT_FOUND_DATA(304, NOT_FOUND, "Request not found");

    private final int businessErrorCode;
    private final String businessErrorDescription;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int businessErrorCode, HttpStatus httpStatus, String businessErrorDescription) {
        this.businessErrorCode = businessErrorCode;
        this.businessErrorDescription = businessErrorDescription;
        this.httpStatus = httpStatus;
    }
}
