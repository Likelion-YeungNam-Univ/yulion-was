package org.example.yulion.global.exception;

/**
 * BaseException은 모든 예외의 부모 클래스 임.
 * RuntimeException을 상속받아 예외를 발생시킨다.
 *
 * Author: 안성윤
 */
public abstract class BaseException extends RuntimeException {
    public abstract BaseExceptionType exceptionType();

}
