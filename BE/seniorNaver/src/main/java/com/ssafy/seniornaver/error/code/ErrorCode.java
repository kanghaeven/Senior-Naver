package com.ssafy.seniornaver.error.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "001", "exception test"),
    NOT_EXISTS_DATA(HttpStatus.BAD_REQUEST, "002", "존재하지 않는 데이터입니다."),
    NOT_VALID_DATA(HttpStatus.BAD_REQUEST, "003", "유효하지 않는 데이터입니다."),
    INVALID_DATA_TYPE(HttpStatus.BAD_REQUEST, "004", "잘못된 데이터 타입입니다."),
    ALREADY_REGISTERED_DATA(HttpStatus.BAD_REQUEST, "005", "이미 존재하는 데이터입니다."),
//    FORBIDDEN_ROLE(HttpStatus.FORBIDDEN, "006", "해당 Role이 아닙니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "007", "지원하지 않는 미디어 유형입니다."),

    // 인증 && 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈 값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    FORBIDDEN_ROLE(HttpStatus.FORBIDDEN, "A-008", "해당 Role이 아닙니다."),

    // 유저
    NOT_EXISTS_USER_ID(HttpStatus.BAD_REQUEST, "U-001", "존재하지 않는 유저 아이디입니다."),
    NOT_EXISTS_USER_NICKNAME(HttpStatus.BAD_REQUEST, "U-002", "존재하지 않는 유저 닉네임입니다."),
    NOT_EXISTS_USER_EMAIL(HttpStatus.BAD_REQUEST, "U-003", "존재하지 않는 유저 이메일입니다."),
    NOT_EXISTS_PROVIDER(HttpStatus.BAD_REQUEST, "U-004", "존재하지 않는 소셜입니다."),
    ALREADY_REGISTERED_USER_ID(HttpStatus.BAD_REQUEST, "U-006", "이미 존재하는 유저 아이디입니다."),
    NOT_EXISTS_USER_PASSWORD(HttpStatus.BAD_REQUEST, "U-007", "존재하지 않는 유저 비밀번호입니다."),
    INVALID_USER_DATA(HttpStatus.BAD_REQUEST, "U-008", "잘못된 유저 정보입니다."),
    NOT_EXISTS_FOLLOWER(HttpStatus.BAD_REQUEST, "U-009", "존재하지 않는 팔로워입니다."),
    NOT_EXISTS_FOLLOWING(HttpStatus.BAD_REQUEST, "U-010", "존재하지 않는 팔로윙입니다."),
    INVALID_ADMIN(HttpStatus.BAD_REQUEST, "U-011", "Admin은 제외 시켜주세요."),

    // 게시판
    NOT_EXISTS_BOARD_ID(HttpStatus.BAD_REQUEST, "B-001", "존재하지 않는 게시판 아이디입니다."),
    NOT_EXISTS_BOARD_CATEGORY(HttpStatus.BAD_REQUEST, "B-002", "존재하지 않는 게시판 카테고리입니다."),
    NOT_EXISTS_BOARD_AUTHORIZE(HttpStatus.BAD_REQUEST, "B-003", "존재하지 않는 게시판 권한입니다."),
    NOT_EXISTS_BOARD_USER(HttpStatus.BAD_REQUEST, "B-004", "존재하지 않는 게시판 유저입니다."),
    NOT_EXISTS_BOARD_COMMENT(HttpStatus.BAD_REQUEST, "B-005", "존재하지 않는 게시판 댓글입니다."),
    NOT_EXISTS_BOARD_FEEDS(HttpStatus.BAD_REQUEST, "B-006", "존재하지 않는 게시판 피드입니다."),
    NOT_VALID_BOARD_DATA(HttpStatus.BAD_REQUEST, "B-007", "유효하지 않는 게시판 데이터입니다."),
    INVALID_SHORTS_BOARD_DATA_TYPE(HttpStatus.BAD_REQUEST, "B-008", "잘못된 게시판 데이터 타입입니다."),
    ALREADY_REGISTERED_BOARD_DATA(HttpStatus.BAD_REQUEST, "B-009", "이미 존재하는 게시판 데이터입니다."),
    UNSUPPORTED_BOARD_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "B-0010", "지원하지 않는 게시판 미디어 유형입니다."),
    NOT_VALID_BOARD_TITLE(HttpStatus.BAD_REQUEST, "B-011", "이모티콘 혹은 욕설을 금지합니다."),

    // 챌린지
    NOT_EXISTS_CHALLENGE_ID(HttpStatus.BAD_REQUEST, "C-001", "존재하지 않는 챌린지 아이디입니다."),
    NOT_EXISTS_CHALLENGE_IMAGE_TYPE(HttpStatus.BAD_REQUEST, "C-002", "존재하지 않는 챌린지 이미지 확장자입니다."),
    NOT_EXISTS_CHALLENGE_CERT_TIME(HttpStatus.BAD_REQUEST, "C-003", "존재하지 않는 챌린지 인증 시간입니다."),
    NOT_EXISTS_CHALLENGE_USER(HttpStatus.BAD_REQUEST, "C-004", "해당 챌린지에 참여 중이지 않습니다."),
    NOT_VALID_CHALLENGE_CERT(HttpStatus.BAD_REQUEST, "C-005", "유효하지 않는 챌린지 인증 방식입니다."),
    NOT_VALID_CHALLENGE_DATE(HttpStatus.BAD_REQUEST, "C-006", "유효하지 않는 챌린지 기간입니다."),
    NOT_EXISTS_CHALLENGE_CERT(HttpStatus.BAD_REQUEST, "C-007", "존재하지 않는 챌린지 인증입니다."),
    ALREADY_REGISTERED_CHALLENGE(HttpStatus.BAD_REQUEST, "C-008", "중복된 챌린지 참여입니다."),
    NOT_EXISTS_CHALLENGE_SESSION(HttpStatus.BAD_REQUEST, "C-009", "존재하지 않는 챌린지 세션입니다."),
    INVALID_SHORTS_CHALLENGE_DATA_TYPE(HttpStatus.BAD_REQUEST, "C-010", "잘못된 챌린지 데이터 타입입니다."),
    ALREADY_REGISTERED_CHALLENGE_DATA(HttpStatus.BAD_REQUEST, "C-011", "이미 존재하는 챌린지 데이터입니다."),
    UNSUPPORTED_CHALLENGE_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "C-012", "지원하지 않는 챌린지 미디어 유형입니다."),
    OVERLAPPED_CHALLENGE_REQUEST(HttpStatus.BAD_REQUEST, "C-013", "기존에 참여중인 챌린지와 중복되는 스케줄입니다."),
    NOT_VALID_CHALLENGE_CERT_TIME(HttpStatus.BAD_REQUEST, "C-014", "챌린지 인증시간이 아닙니다."),

    // 쇼츠
    NOT_EXISTS_SHORTS_ID(HttpStatus.BAD_REQUEST, "S-001", "존재하지 않는 쇼츠 아이디입니다."),
    NOT_EXISTS_SHORTS_COMMENT(HttpStatus.BAD_REQUEST, "S-002", "존재하지 않는 쇼츠 댓글입니다."),
    NOT_VALID_SHORTS_DATA(HttpStatus.BAD_REQUEST, "S-003", "유효하지 않는 쇼츠 데이터입니다."),
    INVALID_SHORTS_DATA_TYPE(HttpStatus.BAD_REQUEST, "S-004", "잘못된 쇼츠 데이터 타입입니다."),
    ALREADY_REGISTERED_SHORTS_DATA(HttpStatus.BAD_REQUEST, "S-005", "이미 존재하는 쇼츠 데이터입니다."),
    UNSUPPORTED_SHORTS_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "S-006", "지원하지 않는 쇼츠 미디어 유형입니다."),

    // 메시지
    NOT_EXISTS_MESSAGE_ID(HttpStatus.BAD_REQUEST, "M-001", "존재하지 않는 메시지입니다."),
    NOT_EXISTS_MESSAGE_RECEIVER(HttpStatus.BAD_REQUEST, "M-002", "존재하지 않는 메시지 RECEIVER 입니다."),
    NOT_EXISTS_MESSAGE_SENDER(HttpStatus.BAD_REQUEST, "M-003", "존재하지 않는 메시지 SENDER 입니다."),
    NOT_EXISTS_MESSAGE_AUTHORIZE(HttpStatus.BAD_REQUEST, "M-004", "존재하지 않는 메시지 권한입니다."),

    // S3
    INVALID_FILE_CONVERT(HttpStatus.BAD_REQUEST, "S3-001", "MultipartFile -> File 변환을 실패했습니다."),
    FAIL_DELETE_FILE(HttpStatus.BAD_REQUEST, "S3-002", "S3에 업로드 된 파일을 지울 때 오류가 발생하였습니다."),

    //포인트 관련
    INSUFFICIENT_POINT(HttpStatus.BAD_REQUEST, "P-001", "포인트가 부족합니다.")
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
