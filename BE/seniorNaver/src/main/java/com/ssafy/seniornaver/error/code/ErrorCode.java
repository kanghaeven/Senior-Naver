package com.ssafy.seniornaver.error.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 인증 && 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-003", "해당 refresh token은 만료됐습니다."),

    // 유저
    NOT_EXISTS_USER_ID(HttpStatus.BAD_REQUEST, "U-001", "존재하지 않는 유저 아이디입니다."),
    NOT_EXISTS_PROVIDER(HttpStatus.BAD_REQUEST, "U-002", "존재하지 않는 소셜입니다."),
    ALREADY_REGISTERED_USER_ID(HttpStatus.BAD_REQUEST, "U-003", "이미 존재하는 유저 아이디입니다."),
    NOT_EXISTS_USER_PASSWORD(HttpStatus.BAD_REQUEST, "U-004", "존재하지 않는 유저 비밀번호입니다."),
    INVALID_ADMIN(HttpStatus.BAD_REQUEST, "U-005", "Admin은 제외 시켜주세요."),

    // 사전
    ALREADY_REGISTERED_VOCA_LIST(HttpStatus.BAD_REQUEST, "D-001", "이미 단어장을 가지고 있는 유저입니다."),
    NOT_EXIST_VOCA_LIST(HttpStatus.BAD_REQUEST, "D-002", "존재하지 않는 단어장 입니다. 단어장을 생성해주세요."),
    NOT_EXIST_WORD(HttpStatus.BAD_REQUEST, "D-003", "존재하지 않는 단어입니다."),
    DONT_AUTHENTICATION_ROLE(HttpStatus.FORBIDDEN, "D-004", "당신 따위에겐 없는 권한입니다. 썩 물러가십쇼"),

    ALREADY_REGISTERED_PROBLEM(HttpStatus.BAD_REQUEST, "SP-001", "이미 등록된 문제 제목입니다."),
    NOT_EXIST_PROBLEM(HttpStatus.BAD_REQUEST,"SP-002", "문제가 존재하지 않습니다. 문제 ID를 확인해주세요"),
    NOT_EXIST_PROBLEM_IN_VOCA_LIST(HttpStatus.BAD_REQUEST, "SP-003", "단어장에 문제가 존재하지 않습니다."),
    NOT_EXIST_ANSWER(HttpStatus.BAD_REQUEST, "SP-004", "정답 혹은 답변을 선택하지 않으셨습니다."),

    NOT_MATCH_CATEGORY(HttpStatus.BAD_REQUEST, "V-001", "요청 목록과 메서드가 상이합니다."),

    // 장소
    DONT_SUCH_PLACE(HttpStatus.BAD_REQUEST, "P-001", "데이터가 부족합니다. 응답 값을 찾을 수 없습니다."),
    
    DONT_SUCH_JOB_POST(HttpStatus.BAD_REQUEST, "J-001", "존재하지 않는 검색 유형입니다."),
    DONT_PARSE_RESPONSE_DATA(HttpStatus.BAD_GATEWAY, "J-002", "확인할수 없는 데이터입니다."),

    //google cloud
    DIALOGFLOW_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-001", "Dialogflow API 에러 발생"),
    API_NOT_FOUND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-002", "API연결 에러 발생"),

    //스크랩
    DUPLICATED_SCRAP(HttpStatus.BAD_REQUEST, "SC-001", "이미 스크랩한 내용입니다.")

    ;

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
