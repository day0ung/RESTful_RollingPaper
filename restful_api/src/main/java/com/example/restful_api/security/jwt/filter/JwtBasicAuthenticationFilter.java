package com.example.restful_api.security.jwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.restful_api.domain.user.User;
import com.example.restful_api.domain.user.UserRepository;
import com.example.restful_api.security.auth.AuthLoginUser;
import com.example.restful_api.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JwtBasicAuthenticationFilter extends BasicAuthenticationFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;

    public JwtBasicAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("인증 혹은 권한 주소 요청 , Authorization : {}");
        String jwtHeader = request.getHeader("Authorization");
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }

        String jwtToken = jwtTokenProvider.extractToken(request);
        String email = jwtTokenProvider.verifyToken(jwtToken);

        if(email != null){
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

            List<GrantedAuthority> authorities = Collections.
                    singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            AuthLoginUser authLoginUser = new AuthLoginUser(user.getEmail(), user.getPassword(), authorities);

            //Jwt 토큰 서명을 통해서 서명이 정상이면  Authentication객체 생성
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(authLoginUser, null, authLoginUser.getAuthorities());

            //강제로 시큐리티의 세션에 접근하여 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);
        }


    }
}
