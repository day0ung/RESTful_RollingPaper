package com.example.restful_api.api.controller;


import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.paper.PaperResponse;
import com.example.restful_api.api.dto.paper.PaperPostRequest;
import com.example.restful_api.service.PaperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/paper")
@RestController
public class PaperContorller {

    private final PaperService paperService;

    @PostMapping
    public BaseResponse<PaperResponse> createPaper(@RequestBody @Validated PaperPostRequest paperPostRequest){
        PaperResponse result = paperService.save(paperPostRequest);
        return BaseResponse.setSuccess(result);

    }

}
