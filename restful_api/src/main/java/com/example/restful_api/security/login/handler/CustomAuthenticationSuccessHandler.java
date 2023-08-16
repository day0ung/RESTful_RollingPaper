package com.example.restful_api.security.login.handler;

import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.security.CustomUserPrincipal;
import com.example.restful_api.security.jwt.JwtTokenProvider;
import com.example.restful_api.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        String jwtToken = jwtTokenProvider.createToken(customUserPrincipal);

        jwtTokenProvider.sendToken(response, jwtToken);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(GsonUtil.toJson(BaseResponse.set(HttpStatus.OK, "Login Success: " + customUserPrincipal.getUser().getEmail())));
    }
}
