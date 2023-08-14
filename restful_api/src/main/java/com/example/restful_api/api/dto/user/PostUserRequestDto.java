package com.example.restful_api.api.dto.user;

import com.example.restful_api.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserRequestDto {
    String name;
    String email;
    String password;

    public User toEntity(){
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
