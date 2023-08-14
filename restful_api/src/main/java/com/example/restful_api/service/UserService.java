package com.example.restful_api.service;

import com.example.restful_api.api.dto.user.PostUserRequestDto;
import com.example.restful_api.domain.user.Provider;
import com.example.restful_api.domain.user.Role;
import com.example.restful_api.domain.user.Users;
import com.example.restful_api.domain.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Users save(PostUserRequestDto dto){
        Users users = Users.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .provider(Provider.LOCAL)
                .build();
        return usersRepository.save(users);
    }

}
