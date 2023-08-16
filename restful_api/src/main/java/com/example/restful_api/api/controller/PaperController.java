package com.example.restful_api.api.controller;


import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.paper.PaperPostRequest;
import com.example.restful_api.api.dto.paper.PaperPutRequest;
import com.example.restful_api.api.dto.paper.PaperResponse;
import com.example.restful_api.service.PaperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/paper")
@RestController
public class PaperContorller {

    private final PaperService paperService;

    @PostMapping
    public ResponseEntity<BaseResponse<PaperResponse>> createPaper(@RequestBody PaperPostRequest request){
        PaperResponse response = paperService.createPaper(request);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.CREATED);
    }

    @PutMapping({"/{paperId}"})
    public ResponseEntity<BaseResponse<PaperResponse>> updatePaper(@PathVariable("paperId") Long paperId,
                                                                   @RequestBody PaperPutRequest request){
        PaperResponse response = paperService.updatePaper(paperId ,request);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.CREATED);
    }


    @DeleteMapping({"/{paperId}"})
    public ResponseEntity<BaseResponse<PaperResponse>> deletePaper(@PathVariable("paperId") Long paperId){
        PaperResponse response = paperService.deletePaper(paperId);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.OK);
    }


}
