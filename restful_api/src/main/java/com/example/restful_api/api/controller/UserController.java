package com.example.restful_api.api.controller;

import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.user.UserPostRequest;
import com.example.restful_api.api.dto.user.UserResponse;
import com.example.restful_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserContoller {

    private final UserService userService;

    @PostMapping("/signup")
    public BaseResponse<?> signUp(@RequestBody UserPostRequest userPostRequest) throws Exception {
        UserResponse result = userService.signUp(requestBody);
        return BaseResponse.setSuccess("ok");
    }
}
