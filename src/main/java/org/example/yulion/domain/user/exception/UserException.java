package org.example.yulion.domain.user.exception;

import org.example.yulion.global.exception.BaseException;
import org.example.yulion.global.exception.BaseExceptionType;

public class UserException extends BaseException {
    private final UserExceptionType userExceptionType;

    public UserException(final UserExceptionType userExceptionType){
        this.userExceptionType = userExceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return userExceptionType;
    }
}
