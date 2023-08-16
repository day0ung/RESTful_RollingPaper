package com.example.restful_api.api.controller;

import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.user.UserPostRequest;
import com.example.restful_api.api.dto.user.UserPutRequest;
import com.example.restful_api.api.dto.user.UserResponse;
import com.example.restful_api.security.CustomUserPrincipal;
import com.example.restful_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<UserResponse>> signUp(@RequestBody UserPostRequest request) throws Exception {
        UserResponse result = userService.signUp(request);
        return new ResponseEntity<> (BaseResponse.setSuccess(result), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BaseResponse<UserResponse>> updateUser(@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
                                                                 @RequestBody UserPutRequest request) throws Exception {
        UserResponse result = userService.updateUser(customUserPrincipal, request);
        return new ResponseEntity<> (BaseResponse.setSuccess(result), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<UserResponse>> deleteUser(@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal) throws Exception {
        UserResponse result = userService.deleteUser(customUserPrincipal);
        return new ResponseEntity<> (BaseResponse.setSuccess(result), HttpStatus.OK);
    }
}
