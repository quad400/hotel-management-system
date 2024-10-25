package com.coderblack.hms.handler;

import com.coderblack.hms.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountNotFoundException;

import java.util.HashSet;
import java.util.Set;

import static com.coderblack.hms.handler.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(AccountNotFoundException exp) {
        return ResponseEntity.status(NOT_FOUND).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(NOT_FOUND_DATA.getBusinessErrorCode())
                        .businessErrorDescription(NOT_FOUND_DATA.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AccountTokenExpireException.class)
    public ResponseEntity<ExceptionResponse> handleException(AccountTokenExpireException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(BAD_REQUEST_DATA.getBusinessErrorCode())
                        .businessErrorDescription(BAD_REQUEST_DATA.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidTokenException exp) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(UNAUTHORIZED_USER.getBusinessErrorCode())
                        .businessErrorDescription(UNAUTHORIZED_USER.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ExceptionResponse> handleException(ServletException exp) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(UNAUTHORIZED_USER.getBusinessErrorCode())
                        .businessErrorDescription(UNAUTHORIZED_USER.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionResponse> handleException(ConflictException exp) {
        return ResponseEntity.status(CONFLICT).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(UNIQUE_DATA.getBusinessErrorCode())
                        .businessErrorDescription(UNIQUE_DATA.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleException(ExpiredJwtException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(BAD_REQUEST_DATA.getBusinessErrorCode())
                        .businessErrorDescription(BAD_REQUEST_DATA.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleException(UnauthorizedException exp) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(UNAUTHORIZED_USER.getBusinessErrorCode())
                        .businessErrorDescription(UNAUTHORIZED_USER.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleException(AuthorizationDeniedException exp) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(UNAUTHORIZED_USER.getBusinessErrorCode())
                        .businessErrorDescription(UNAUTHORIZED_USER.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionResponse> handleException(SignatureException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(BAD_REQUEST_DATA.getBusinessErrorCode())
                        .businessErrorDescription(BAD_REQUEST_DATA.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(BAD_REQUEST_DATA.getBusinessErrorCode())
                        .businessErrorDescription("Invalid User Authentication Credentials")
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AccountBlockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(AccountBlockedException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(BAD_REQUEST_DATA.getBusinessErrorCode())
                        .businessErrorDescription(BAD_REQUEST_DATA.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleException(IllegalArgumentException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(BAD_REQUEST_DATA.getBusinessErrorCode())
                        .businessErrorDescription(BAD_REQUEST_DATA.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ExceptionResponse> handleException(NumberFormatException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(BAD_REQUEST_DATA.getBusinessErrorCode())
                        .businessErrorDescription(BAD_REQUEST_DATA.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(NotFoundException exp) {
        return ResponseEntity.status(NOT_FOUND).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(NOT_FOUND_DATA.getBusinessErrorCode())
                        .businessErrorDescription(NOT_FOUND_DATA.getBusinessErrorDescription())
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleException(HttpMessageNotReadableException exp) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(
                ExceptionResponse
                        .builder()
                        .businessErrorCode(UNPROCESSABLE.getBusinessErrorCode())
                        .businessErrorDescription(UNPROCESSABLE.getBusinessErrorDescription())
                        .error("Required request body is missing")
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionResponse
                        .builder()
                        .businessErrorDescription("Please contact admin for this error, coderblack@gmail.com")
                        .error(exp.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentValidException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors().forEach(error -> {
            var errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });

        return ResponseEntity.status(UNPROCESSABLE_ENTITY)
                .body(
                        ExceptionResponse
                                .builder()
                                .businessErrorCode(UNPROCESSABLE.getBusinessErrorCode())
                                .businessErrorDescription(UNPROCESSABLE.getBusinessErrorDescription())
                                .validationErrors(errors)
                                .build()
                );
    }
}