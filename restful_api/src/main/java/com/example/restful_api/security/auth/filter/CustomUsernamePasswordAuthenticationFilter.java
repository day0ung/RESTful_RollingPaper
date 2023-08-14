package com.example.restful_api.security.auth.filter;

import com.example.restful_api.domain.user.Users;
import com.example.restful_api.security.jwt.JwtTokenProvider;
import com.example.restful_api.security.CustomUserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Users users = objectMapper.readValue(request.getInputStream(), Users.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(users.getEmail(), users.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
            log.info("LoginUser : {}", customUserPrincipal);
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Login successfulAuthentication()");

        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authResult.getPrincipal();
        String jwtToken = jwtTokenProvider.createToken(customUserPrincipal);
        jwtTokenProvider.sendToken(response, jwtToken);

    }
}
