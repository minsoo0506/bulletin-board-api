package com.mnsoo.board.controller;

import com.mnsoo.board.dto.user.AuthDto;
import com.mnsoo.board.dto.SuccessResponse;
import com.mnsoo.board.service.UserService;
import com.mnsoo.board.type.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "신규 회원 등록")
    @PostMapping("/auth/signup")
    public ResponseEntity<SuccessResponse<String>> signup(@RequestBody @Valid AuthDto.SignUp signupRequest) {

        userService.addUser(signupRequest);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.SIGNUP_SUCCESS,
                        "success"
                )
        );
    }

    @Operation(summary = "로그인", description = "등록된 사용자인지 확인 및 토큰 발급")
    @PostMapping("/auth/signin")
    public ResponseEntity<SuccessResponse<String>> signIn(@RequestBody AuthDto.SignIn signInRequest){

        userService.signIn(signInRequest);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.LOGIN_SUCCESS,
                        "success"
                )
        );
    }
}
