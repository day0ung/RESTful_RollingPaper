package com.example.restful_api.service;

import com.example.restful_api.api.dto.user.PostUserRequestDto;
import com.example.restful_api.domain.user.User;
import com.example.restful_api.domain.user.UserRepository;
import com.example.restful_api.security.auth.AuthLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(PostUserRequestDto dto){
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        return userRepository.save(user);
    }

}
