package org.example.yulion.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.yulion.global.config.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 커스텀되는 예외사항들을 처리하는 ExceptionHandler
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 각 도메인 패키지별로 선언한 Exception들을 처리해주는 메소드
     * baseException을 받아와 처리한다.
     */
    @ExceptionHandler(BaseException.class)
    public ApiResponse<ApiExceptionResponse> handleBaseException(final BaseException ex){
        final BaseExceptionType baseExceptionType = ex.exceptionType();
        final ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                baseExceptionType.errorCode(), baseExceptionType.errorMessage()
        );
        log.info("error = {}", apiExceptionResponse);

        return ApiResponse.createError(apiExceptionResponse);
    }

    // apiExceptionResponse 생성해주는 메소드 만들어도될듯?
    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse<ApiExceptionResponse> handleBadCredentialsException(final BadCredentialsException ex){
        final CommonExceptionType commonExceptionType = CommonExceptionType.INVALID_CREDENTIALS;

        return createResponse(commonExceptionType);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiResponse<ApiExceptionResponse> handleDataIntegrityViolationException(final DataIntegrityViolationException ex){
        if (ex.getCause() instanceof DuplicateKeyException){
            CommonExceptionType commonExceptionType = CommonExceptionType.DUPLICATED_FIELD;
            commonExceptionType.setErrorMessage(ex.getLocalizedMessage());
            return createResponse(commonExceptionType);
        } else {
            return handleException(ex);
        }

    }

    @ExceptionHandler(Exception.class)
    protected ApiResponse<ApiExceptionResponse> handleException(Exception ex) {
        log.error("GlobalExceptionHandler에 정의되지 않은 에러: " + ex.toString());

        final ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                500,
                ex.toString()
        );
        return ApiResponse.createError(apiExceptionResponse);
    }

    public ApiResponse<ApiExceptionResponse> createResponse(BaseExceptionType ex){
        final ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                ex.errorCode(),
                ex.errorMessage()
        );

        return ApiResponse.createError(apiExceptionResponse);
    }
}
