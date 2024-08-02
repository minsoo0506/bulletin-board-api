package com.mnsoo.board.service;

import static com.mnsoo.board.util.CookieUtil.createCookie;

import com.mnsoo.board.domain.User;
import com.mnsoo.board.dto.AuthDto;
import com.mnsoo.board.dto.UserDto;
import com.mnsoo.board.exception.RestApiException;
import com.mnsoo.board.repository.UserRepository;
import com.mnsoo.board.type.ErrorCode;
import com.mnsoo.board.type.TokenType;
import com.mnsoo.board.type.UserRole;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
public class UserService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // 신규 사용자 정보 등록
    public void addUser(AuthDto.SignUp signupRequest) {

        log.info("Adding new user '{}'", signupRequest.getUserDto().getLoginId());

        UserDto userDto = signupRequest.getUserDto();

        if(userRepository.existsByLoginId(userDto.getLoginId())){
            log.warn("Already registered user : '{}'", userDto.getLoginId());
            throw new RestApiException(ErrorCode.ALREADY_REGISTERED_USER);
        }

        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_USER);

        // User 엔티티에 정보 저장후 레포지토리에 저장
        User user = User.builder()
                .loginId(userDto.getLoginId())
                .password(passwordEncoder.encode(userDto.getPassword())) // 비밀번호 암호화
                .email(userDto.getEmail())
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .roles(roles)
                .build();

        userRepository.save(user);

        log.info("User '{}' Registered Successfully", user.getLoginId());

        // access 토큰과 refresh 토큰 생성
        String access = jwtService
                .createJwt(TokenType.ACCESS.getValue(), user.getEmail(), userDto.getRoles(), TokenType.ACCESS.getExpiryTime());
        String refresh = jwtService
                .createJwt(TokenType.REFRESH.getValue(), user.getEmail(), userDto.getRoles(), TokenType.REFRESH.getExpiryTime());

        // response : 쿠키에 refresh 토큰을 넣고 헤더에 access 토큰을 넣어보냄 (보안상)
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        if(response != null) {
            response.addCookie(createCookie(TokenType.REFRESH.getValue(), refresh));
            response.setHeader(TokenType.ACCESS.getValue(), access);
            log.info("JWT added to response for user '{}'", user.getLoginId());
        }
    }
}
