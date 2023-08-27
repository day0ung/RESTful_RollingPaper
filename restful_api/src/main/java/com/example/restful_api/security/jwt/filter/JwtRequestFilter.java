package com.example.restful_api.security.jwt.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.restful_api.security.CustomUserPrincipal;
import com.example.restful_api.security.jwt.JwtTokenProvider;
import com.example.restful_api.security.login.AuthLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthLoginService authLoginService;

    private final List<String> allowedPath = Arrays.asList("/api/user/signup", "/api/user/signin");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();

        if (allowedPath.contains(requestPath) || request.getMethod().equals("GET")) {
            filterChain.doFilter(request, response);
            return;
        }


        final String requestTokenHeader = request.getHeader("Authorization");
        String email = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = jwtTokenProvider.extractToken(request);
            try {
                email = jwtTokenProvider.verifyToken(jwtToken);
                log.debug("email = {}", email);
            } catch (IllegalArgumentException e) {
                log.error("Unable to get JWT Token");
            } catch (TokenExpiredException e) {
                log.error("JWT Token has expired");
            } catch (SignatureVerificationException e){
                log.error("The Token's Signature resulted invalid , {}", e.getMessage());
            } catch (Exception e){
                log.error("Error ");
            }
        }

        // 토큰을 받으면 유효성을 검사.
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authLoginService.loadUserByUsername(email);
            //Jwt 토큰 서명을 통해서 서명이 정상이면  Authentication객체 생성
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(customUserPrincipal, null, customUserPrincipal.getAuthorities());
            //강제로 시큐리티의 세션에 접근하여 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
