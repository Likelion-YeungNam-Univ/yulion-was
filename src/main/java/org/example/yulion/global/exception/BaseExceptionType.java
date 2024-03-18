package org.example.yulion.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

    int errorCode();
    HttpStatus httpStatus();
    String errorMessage();
    void setErrorMessage(String message);
}
