package com.example.restful_api.api.controller;

import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.user.PostUserRequestDto;
import com.example.restful_api.service.UserService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserContoller {

    private final UserService userService;

    @PostMapping("/signin")
    public BaseResponse<?> signUp(@RequestBody PostUserRequestDto postUserRequestDto) throws Exception {
        userService.save(postUserRequestDto);
        return BaseResponse.setSuccess("ok");
    }
}
