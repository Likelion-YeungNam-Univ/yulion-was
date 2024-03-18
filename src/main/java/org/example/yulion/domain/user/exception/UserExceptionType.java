package org.example.yulion.domain.user.exception;

import org.example.yulion.global.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum UserExceptionType implements BaseExceptionType {
    USER_NOT_FOUND(404,
            HttpStatus.BAD_REQUEST,
            "존재하지 않는 User 입니다.");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private String errorMessage;

    UserExceptionType(final int errorCode, final HttpStatus httpStatus, final String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }


    @Override
    public int errorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }

    @Override
    public void setErrorMessage(String message) {
        this.errorMessage += message;
    }
}