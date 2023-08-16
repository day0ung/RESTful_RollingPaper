package com.example.restful_api.service;

import com.example.restful_api.api.dto.user.UserPostRequest;
import com.example.restful_api.domain.user.Provider;
import com.example.restful_api.domain.user.Role;
import com.example.restful_api.domain.user.User;
import com.example.restful_api.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(UserPostRequest dto){
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .provider(Provider.LOCAL)
                .build();
        return userRepository.save(user);
    }

}
