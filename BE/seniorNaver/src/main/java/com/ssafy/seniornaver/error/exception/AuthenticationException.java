package com.ssafy.seniornaver.error.exception;

import com.ssafy.seniornaver.error.code.ErrorCode;
import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException{
    private ErrorCode errorCode;

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
