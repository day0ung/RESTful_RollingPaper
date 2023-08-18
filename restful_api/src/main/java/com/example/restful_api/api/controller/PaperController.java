package com.example.restful_api.api.controller;


import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.paper.PaperPostRequest;
import com.example.restful_api.api.dto.paper.PaperPutRequest;
import com.example.restful_api.api.dto.paper.PaperResponse;
import com.example.restful_api.security.CustomUserPrincipal;
import com.example.restful_api.service.PaperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/paper")
@RestController
public class PaperController {

    private final PaperService paperService;

    @PostMapping
    public ResponseEntity<BaseResponse<PaperResponse>> createPaper(@RequestBody PaperPostRequest request,
                                                                   @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal){
        PaperResponse response = paperService.createPaper(request, customUserPrincipal);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.CREATED);
    }

    @PutMapping({"/{paperId}"})
    public ResponseEntity<BaseResponse<PaperResponse>> updatePaper(@PathVariable("paperId") Long paperId,
                                                                   @RequestBody PaperPutRequest request,
                                                                   @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal){
        PaperResponse response = paperService.updatePaper(paperId ,request, customUserPrincipal);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.CREATED);
    }

    @DeleteMapping({"/{paperId}"})
    public ResponseEntity<BaseResponse<PaperResponse>> deletePaper(@PathVariable("paperId") Long paperId,
                                                                   @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal){
        PaperResponse response = paperService.deletePaper(paperId, customUserPrincipal);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<BaseResponse<?>> searchPaperList(@RequestParam(required = false) String searchWord,
                                                                       Pageable pageable){
        BaseResponse<?> response = paperService.searchPaperList(searchWord, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping({"/{paperId}"})
    public ResponseEntity<BaseResponse<PaperResponse>> getPaper(@PathVariable Long paperId){
        PaperResponse response = paperService.getPaper(paperId);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.OK);
    }


}
