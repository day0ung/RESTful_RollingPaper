package com.example.restful_api.security.oauth2.handler;

import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.security.jwt.JwtTokenProvider;
import com.example.restful_api.security.CustomUserPrincipal;
import com.example.restful_api.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("OAuth2LoginSuccessHandler");
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        String jwtToken = jwtTokenProvider.createToken(customUserPrincipal);
        jwtTokenProvider.sendToken(response, jwtToken);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(GsonUtil.toJson(BaseResponse.set(HttpStatus.OK, "Social Login Success: " + customUserPrincipal.getUser().getEmail())));
    }

}
