package com.ssafy.seniornaver.error;

import com.ssafy.seniornaver.error.exception.AuthenticationException;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import com.ssafy.seniornaver.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    // 비즈니스 오류
    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        log.error("BadRequestException", e);
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode().getHttpStatus().toString(), e.getErrorCode().getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }
    // Authentication 관련 ExceptionHandler
    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        log.error("AuthenticationException", e);
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode().getHttpStatus().toString(), e.getErrorCode().getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }
    // 지원하지 않는 Http Method 관련 ExceptionHandler
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED.toString(), "H-000", e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }
    // 그 외 Exception
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "E-000", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
