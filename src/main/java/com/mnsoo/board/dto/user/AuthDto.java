package com.mnsoo.board.dto.user;

import jakarta.validation.Valid;
import lombok.Data;

public class AuthDto {

    @Data
    public static class SignIn {

        private String loginId;
        private String password;
    }

    @Data
    public static class SignUp {

        @Valid
        private UserDto userDto;
    }
}
