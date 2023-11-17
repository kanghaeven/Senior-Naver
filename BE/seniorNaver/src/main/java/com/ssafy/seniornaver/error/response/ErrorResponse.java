package com.ssafy.seniornaver.error.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import com.ssafy.seniornaver.error.exception.DontSuchException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private String httpStatus;
    private String errorCode;
    private String errorMessage;

    public static ErrorResponse of(String httpStatus, String errorCode, String errorMessage) {
        return ErrorResponse.builder()
                .httpStatus(httpStatus)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }

    public static ResponseEntity<ErrorResponse> of(DontSuchException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.builder()
                        .httpStatus(e.getErrorCode().getHttpStatus().toString())
                        .errorCode(e.getErrorCode().getErrorCode())
                        .errorMessage(e.getErrorCode().getMessage())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> of(JsonProcessingException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ErrorResponse.builder()
                        .httpStatus(HttpStatus.BAD_GATEWAY.toString())
                        .errorCode("F-003")
                        .errorMessage("확인할수 없는 데이터입니다.")
                        .build());
    }

    public static ResponseEntity<ErrorResponse> of(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ErrorResponse.builder()
                        .httpStatus(e.getErrorCode().getHttpStatus().toString())
                        .errorCode(e.getErrorCode().getErrorCode())
                        .errorMessage(e.getErrorCode().getMessage())
                        .build());
    }

}