package com.example.restful_api.security.jwt.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.restful_api.domain.user.User;
import com.example.restful_api.domain.user.UserRepository;
import com.example.restful_api.security.CustomUserPrincipal;
import com.example.restful_api.security.auth.filter.CustomUsernamePasswordAuthenticationFilter;
import com.example.restful_api.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String email = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            log.debug("========================================= jwtToken = {}", requestTokenHeader.substring(7));
            jwtToken = jwtTokenProvider.extractToken(request);
            try {
                email = jwtTokenProvider.verifyToken(jwtToken);
                log.debug("email = {}", email);
            } catch (IllegalArgumentException e) {
                log.error("Unable to get JWT Token");
            } catch (TokenExpiredException e) {
                log.error("JWT Token has expired");
            }
        } else {
            //logger.warn("JWT Token does not begin with Bearer String");
        }

        // 토큰을 받으면 유효성을 검사.
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserPrincipal userPrincipal = this.

                    User user = userRepository.findByEmail(email)
//                        .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));
//
//
//                CustomUserPrincipal customUserPrincipal = new CustomUserPrincipal(user);
//
//                //Jwt 토큰 서명을 통해서 서명이 정상이면  Authentication객체 생성
//                Authentication authentication =
//                        new UsernamePasswordAuthenticationToken(customUserPrincipal, null, customUserPrincipal.getAuthorities());
//
//                //강제로 시큐리티의 세션에 접근하여 Authentication 객체 저장
//                SecurityContextHolder.getContext().setAuthentication(authentication);
                    UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            log.debug("userDetails = {}", userDetails);
            // 토큰이 유효한 경우.. 수동으로 인증을 설정하도록 Spring Security를 구성
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                log.debug("Token Validate Complete");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 컨텍스트에서 인증을 설정한 후, 현재 사용자가 인증되었음을 지정. Spring Security Configurations 성공적으로 통과.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
//        String jwtToken = jwtTokenProvider.extractToken(request);
//        if(jwtToken != null){
//            String email = "";
//            try {
//                email = jwtTokenProvider.verifyToken(jwtToken);
//                log.info("email = {}", email);
//            } catch (IllegalArgumentException e) {
//                log.error("Unable to get JWT Token");
//            } catch (TokenExpiredException e) {
//                log.error("JWT Token has expired");
//            }
//
//            if (email != null) {
//                User user = userRepository.findByEmail(email)
//                        .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));
//
//
//                CustomUserPrincipal customUserPrincipal = new CustomUserPrincipal(user);
//
//                //Jwt 토큰 서명을 통해서 서명이 정상이면  Authentication객체 생성
//                Authentication authentication =
//                        new UsernamePasswordAuthenticationToken(customUserPrincipal, null, customUserPrincipal.getAuthorities());
//
//                //강제로 시큐리티의 세션에 접근하여 Authentication 객체 저장
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//        filterChain.doFilter(request,response);

    }

}
