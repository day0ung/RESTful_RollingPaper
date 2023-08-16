package com.example.restful_api.api.dto.user;

import com.example.restful_api.domain.user.Provider;
import com.example.restful_api.domain.user.Role;
import com.example.restful_api.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPostRequest {
    String name;
    String email;
    String password;

    public User toEntity(String encodePassword, Role role, Provider provider){
        return User.builder()
                .name(name)
                .email(email)
                .password(encodePassword)
                .role(role)
                .provider(provider)
                .build();
    }
}
