package org.example.yulion.domain.post.exception;

import org.example.yulion.global.exception.BaseException;
import org.example.yulion.global.exception.BaseExceptionType;

public class PostException extends BaseException {
    private final PostExceptionType postExceptionType;

    public PostException(final PostExceptionType postExceptionType) {
        this.postExceptionType = postExceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return postExceptionType;
    }
}
