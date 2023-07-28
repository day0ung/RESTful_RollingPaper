package com.example.restful_api.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.restful_api.security.CustomUserPrincipal;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Getter
@Component
public class JwtTokenProvider {
    @Value("${spring.jwt.sercret") //초기화
    private String secretKey;
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";



    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private final Long accessTokenValidTime = 60 * 60 * 1000L; // 1 hour

    public String createToken(CustomUserPrincipal customUserPrincipal) {
        return JWT.create()
                .withSubject(customUserPrincipal.getUser().getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10)))
                .withClaim("email", customUserPrincipal.getUser().getEmail())
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void sendToken(HttpServletResponse response, String jwtToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(AUTH_HEADER, BEARER + jwtToken);
    }

    public String verifyToken(String token){
        return JWT.require(Algorithm.HMAC512(secretKey)).build()
                .verify(token).getClaim("email").asString(); //ions.TokenExpiredException
    }

    public String extractToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTH_HEADER))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""))
                .orElse(null);
    }
}
