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

    NOT_AUTHENTICATED_USER(HttpStatus.BAD_REQUEST, "인증된 사용자가 아닙니다."),
    UNEXPECTED_PRINCIPAL_TYPE(HttpStatus.BAD_REQUEST, "예상하지 못한 Principal 타입입니다."),

    ALREADY_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 회원가입이 완료된 사용자 압니다."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 로그인 아이디 입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    REFRESH_TOKEN_NULL(HttpStatus.BAD_REQUEST, "Refresh 토큰이 없습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 Refresh 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 Refresh 토큰입니다."),

    REQUIRED_FIELD_NULL(HttpStatus.BAD_REQUEST, "게시물 등록을 위한 필수 정보가 누락되어 있습니다."),
    NOTHING_TO_UPDATE(HttpStatus.BAD_REQUEST, "해당 게시글에 대해 수정할 사항이 없습니다."),
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 게시글을 찾을 수 없습니다."),
    AUTHOR_AND_USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "게시물은 작성자만 수정할 수 있습니다."),

    INVALID_IMAGE_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "유효한 이미지 파일이 아닙니다."),
    INVALID_REVIEW_IMAGE_ACTION(HttpStatus.BAD_REQUEST, "유효하지 않은 이미지 업로드 액션입니다."),
    FAILED_TO_UPLOAD_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다."),
    FAILED_TO_DELETE_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 삭제에 실패했습니다."),

    FAILED_TO_SET_POST_LIKE(HttpStatus.BAD_REQUEST, "좋아요를 누르기 위해서 로그인이 필요합니다."),
    FAILED_TO_WRITE_COMMENT(HttpStatus.BAD_REQUEST, "댓글을 작성하기 위해서 로그인이 필요합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String description;
}
