package com.mnsoo.board.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SIGNUP_SUCCESS("회원가입 성공"),
    LOGIN_SUCCESS("로그인 성공"),
    TOKEN_ISSUED("토큰 재발급 성공"),

    POST_WRITE_SUCCESS("게시글 등록 성공"),
    POST_UPDATE_SUCCESS("게시글 수정 성공"),
    GET_ALL_POSTS_SUCCESS("생성일 기준 내림차순으로 게시글 목록 조회 성공"),
    GET_POSTS_BY_SEARCH_SUCCESS("검색으로 게시글 목록 조회 성공")
    ;

    private final String message;
}
