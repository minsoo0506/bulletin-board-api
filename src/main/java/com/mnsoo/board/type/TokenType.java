package com.mnsoo.board.type;

import lombok.Getter;

@Getter
public enum TokenType {

    ACCESS("access", 600000L), // 10분
    REFRESH("refresh", 86400000L); // 24시간

    private final String value;
    private final long expiryTime;

    TokenType(String value, long expiryTime) {
        this.value = value;
        this.expiryTime = expiryTime;
    }
}
