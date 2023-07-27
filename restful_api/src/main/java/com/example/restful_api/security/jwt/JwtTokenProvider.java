package com.example.restful_api.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.restful_api.security.auth.AuthLoginUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${spring.jwt.sercret") //초기화
    private String secretKey;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private final Long accessTokenValidTime = 60 * 60 * 1000L; // 1 hour

    public String create(AuthLoginUser authLoginUser) {
        return JWT.create()
                .withSubject(authLoginUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10)))
                .withClaim("email", authLoginUser.getEmail())
                .withClaim("password", authLoginUser.getPassword())
                .sign(Algorithm.HMAC512(secretKey));
    }
}
