package com.example.restful_api.api.dto.user;

import lombok.Getter;

@Getter
public class PostUserRequestDto {
    String name;
    String email;
    String password;
}
