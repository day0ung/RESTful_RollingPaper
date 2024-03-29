package com.example.restful_api.config;

import com.example.restful_api.domain.user.Role;
import com.example.restful_api.domain.user.UserRepository;
import com.example.restful_api.security.CustomAuthenticationEntryPoint;
import com.example.restful_api.security.jwt.JwtTokenProvider;
import com.example.restful_api.security.jwt.filter.JwtRequestFilter;
import com.example.restful_api.security.login.AuthLoginService;
import com.example.restful_api.security.login.filter.CustomUsernamePasswordAuthenticationFilter;
import com.example.restful_api.security.login.handler.CustomAuthenticationFailureHandler;
import com.example.restful_api.security.login.handler.CustomAuthenticationSuccessHandler;
import com.example.restful_api.security.oauth2.CustomOAuth2UserService;
import com.example.restful_api.security.oauth2.handler.OAuth2LoginFailureHandler;
import com.example.restful_api.security.oauth2.handler.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthLoginService authLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    private final String LOGIN_FILTER_URL = "/api/user/signin";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * AuthenticationManager 설정 후 등록
     * PasswordEncoder를 사용하는 AuthenticationProvider 지정 (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용)
     * FormLogin(기존 스프링 시큐리티 로그인)과 동일하게 DaoAuthenticationProvider 사용
     * UserDetailsService는 커스텀 LoginService로 등록
     * 또한, FormLogin과 동일하게 AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
     *
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(authLoginService);
        return new ProviderManager(provider);
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(jwtTokenProvider);
    }

    @Bean
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }


    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() {
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl(LOGIN_FILTER_URL);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler());
        return filter;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable() //h2-console
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .cors().configurationSource(corsConfigurationSource())

                .and()
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint())

                .and()
                .authorizeRequests()
                .antMatchers("/api/user/profile").hasRole(Role.USER.name())
                .anyRequest().permitAll()

                .and()
                .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler)
                .userInfoEndpoint().userService(customOAuth2UserService);

        http.addFilterAfter(new CustomUsernamePasswordAuthenticationFilter(authenticationManager()), LogoutFilter.class)
            .addFilterBefore(new JwtRequestFilter(jwtTokenProvider, authLoginService), CustomUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD","PATCH","POST","GET","DELETE","PUT"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("X-AUTH-TOKEN", "REFRESH-TOKEN"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
