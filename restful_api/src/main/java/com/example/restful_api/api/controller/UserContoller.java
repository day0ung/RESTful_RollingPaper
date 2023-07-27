package com.example.restful_api.api.controller;

import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.user.PostUserRequestDto;
import com.example.restful_api.service.UserService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserContoller {

    private final UserService userService;

    @PostMapping("/signin")
    public BaseResponse<?> signUp(@RequestBody PostUserRequestDto postUserRequestDto) throws Exception {
        userService.save(postUserRequestDto);
        return BaseResponse.setSuccess("ok");
    }
}
