package org.example.yulion.domain.comment.exception;

import org.example.yulion.global.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum CommentExceptionType implements BaseExceptionType {
    WRITER_NOT_FOUND(404,
            HttpStatus.NOT_FOUND,
            "존재하지 않는 Writer 입니다."),
    POST_NOT_FOUND(404,
            HttpStatus.NOT_FOUND,
            "존재하지 않는 Post 요청입니다."),
    COMMENT_NOT_FOUND(404,
            HttpStatus.NOT_FOUND,
            "존재하지 않는 Comment 입니다."),

    UNAUTHORIZED_COMMENT(401,
            HttpStatus.UNAUTHORIZED,
            "댓글 작성자만 수정, 삭제가 가능합니다."),

    INVALID_REPLY(400,
            HttpStatus.BAD_REQUEST,
            "댓글의 답글은 2 depth까지만 가능합니다.");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private String errorMessage;

    CommentExceptionType(final int errorCode,
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
