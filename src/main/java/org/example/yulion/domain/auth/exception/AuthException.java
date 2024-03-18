package org.example.yulion.domain.auth.exception;

import org.example.yulion.global.exception.BaseException;
import org.example.yulion.global.exception.BaseExceptionType;

public class AuthException extends BaseException {
    private AuthExceptionType authExceptionType;

    public AuthException(final AuthExceptionType authExceptionType){
        this.authExceptionType = authExceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return authExceptionType;
    }

}
