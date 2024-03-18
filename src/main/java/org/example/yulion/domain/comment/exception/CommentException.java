package org.example.yulion.domain.comment.exception;

import org.example.yulion.global.exception.BaseException;
import org.example.yulion.global.exception.BaseExceptionType;

public class CommentException extends BaseException {
    private final CommentExceptionType commentExceptionType;

    public CommentException(final CommentExceptionType commentExceptionType) {
        this.commentExceptionType = commentExceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return commentExceptionType;
    }
}
