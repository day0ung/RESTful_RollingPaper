package com.example.restful_api.api.controller;


import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.paper.PostPaperRequestDto;
import com.example.restful_api.api.dto.paper.PostPaperResponseDto;
import com.example.restful_api.service.PaperService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/paper")
@RestController
public class PaperContorller {

    private final PaperService paperService;

    @PostMapping
    public BaseResponse<PostPaperResponseDto> createPaper(@RequestBody @Validated PostPaperRequestDto postPaperRequestDto){
        PostPaperResponseDto result = paperService.save(postPaperRequestDto);
        return BaseResponse.setSuccess(result);

    }

}
