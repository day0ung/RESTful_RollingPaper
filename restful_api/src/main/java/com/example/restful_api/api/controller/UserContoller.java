package com.example.restful_api.api.controller;

import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.user.UserPostRequest;
import com.example.restful_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserContoller {

    private final UserService userService;

    @PostMapping("/signin")
    public BaseResponse<?> signUp(@RequestBody UserPostRequest userPostRequest) throws Exception {
        userService.save(userPostRequest);
        return BaseResponse.setSuccess("ok");
    }
}
