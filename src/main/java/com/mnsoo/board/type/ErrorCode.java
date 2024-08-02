package com.mnsoo.board.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서부 오류가 발생했습니다."),

    ALREADY_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 회원가입이 완료된 사용자 압니다."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 로그인 아이디 입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    REFRESH_TOKEN_NULL(HttpStatus.BAD_REQUEST, "Refresh 토큰이 없습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 Refresh 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 Refresh 토큰입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String description;
}
