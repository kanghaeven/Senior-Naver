package com.ssafy.seniornaver.error.exception;

import com.ssafy.seniornaver.error.code.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private ErrorCode errorCode;

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}