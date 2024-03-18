package org.example.yulion.global.exception;

import lombok.Setter;
import org.springframework.http.HttpStatus;

public enum CommonExceptionType implements BaseExceptionType{
    INVALID_CREDENTIALS(
            401,
            HttpStatus.NOT_FOUND,
            "잘못된 User 비밀번호 입니다."
    ),

    DUPLICATED_FIELD(
        409,
        HttpStatus.CONFLICT,
        "중복된 필드가 있습니다. 중복된 필드 : "
    );

    private final int errorCode;
    private final HttpStatus httpStatus;
    private String errorMessage;


    CommonExceptionType(final int errorCode,
                        final HttpStatus httpStatus,
                        final String errorMessage) {
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
