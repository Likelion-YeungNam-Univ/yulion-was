package org.example.yulion.domain.post.exception;

import org.example.yulion.global.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum PostExceptionType implements BaseExceptionType {
    POST_NOT_FOUND(404,
            HttpStatus.NOT_FOUND,
            "존재하지 않는 Post 요청입니다."
    ),

    WRITER_NOT_FOUND(404,
    HttpStatus.NOT_FOUND,
    "존재하지 않는 Writer입니다."
    ),

    UNAUTHORIZED_POST(401,
            HttpStatus.UNAUTHORIZED,
            "게시글 작성자만 수정, 삭제가 가능합니다."
    );

    private final int errorCode;
    private final HttpStatus httpStatus;
    private String errorMessage;

    PostExceptionType(final int errorCode,
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
