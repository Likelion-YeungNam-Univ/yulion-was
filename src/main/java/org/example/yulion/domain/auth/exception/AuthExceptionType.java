package org.example.yulion.domain.auth.exception;

import org.example.yulion.global.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum AuthExceptionType implements BaseExceptionType {
    MEMBER_NOT_FOUND(404,
            HttpStatus.NOT_FOUND,
            "존재하지 않는 User 입니다."
    ),

    INVALID_CREDENTIALS(401,
            HttpStatus.NOT_FOUND,
            "존재하지 않는 User 정보 입니다."
    ),

    DUPLICATE_PHONE_NUMBER(409,
                           HttpStatus.CONFLICT,
    "중복된 전화번호 입니다."),

    DUPLICATE_EMAIL(409,
                    HttpStatus.CONFLICT,
    "중복된 이메일 입니다."),

    WRONG_LOGOUT_TRIAL(403,
    HttpStatus.FORBIDDEN,
    "비정상적인 로그아웃 시도입니다.");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private String errorMessage;

    AuthExceptionType(final int errorCode,
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
