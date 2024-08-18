package com.mnsoo.board.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    private SecretKey secretKey;

    @Autowired
    public JwtService(@Value("${spring.jwt.secret}") String secret){

        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SIG.HS256.key().build().getAlgorithm());
    }

    public String getEmail(String token){

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public List<String> getRoles(String token) {

        List<?> roles = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("roles", List.class);

        if (roles != null) {
            return roles.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String getTokenType(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("tokenType", String.class);
    }

    /**
     * JWT 생성
     *
     * @param tokenType : 토큰 타입(ACCESS 토큰 / REFRESH 토큰)
     * @param email : 유저의 email
     * @param roles : 유저의 권한
     * @param expiredMs : 만료 시점
     * @return JWT
     */
    public String createJwt(String tokenType, String email, List<String> roles, Long expiredMs) {

        return Jwts.builder()
                .claim("tokenType", tokenType)
                .claim("email", email)
                .claim("roles", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
