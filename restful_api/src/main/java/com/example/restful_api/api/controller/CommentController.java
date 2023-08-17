package com.example.restful_api.api.controller;

import com.example.restful_api.api.BaseResponse;
import com.example.restful_api.api.dto.comment.CommentPostRequest;
import com.example.restful_api.api.dto.comment.CommentPutRequest;
import com.example.restful_api.api.dto.comment.CommentResponse;
import com.example.restful_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping({"/paper/{paperId}"})
    public ResponseEntity<BaseResponse<CommentResponse>> createComment(@PathVariable Long paperId,
                                                                       @RequestBody CommentPostRequest request){
        CommentResponse response = commentService.createComment(paperId, request);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.CREATED);
    }

    @PutMapping({"/{commentId}"})
    public ResponseEntity<BaseResponse<CommentResponse>> updateComment(@RequestBody CommentPutRequest request,
                                                                       @PathVariable Long commentId){
        CommentResponse response = commentService.updateComment(commentId, request);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.OK);
    }

    @DeleteMapping({"/{commentId}"})
    public ResponseEntity<BaseResponse<CommentResponse>> deleteComment(@PathVariable Long commentId){
        CommentResponse response = commentService.deleteComment(commentId);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.OK);
    }

    @GetMapping({"/{commentId}"})
    ResponseEntity<BaseResponse<CommentResponse>> getComment(@PathVariable Long commentId){
        CommentResponse response = commentService.getComment(commentId);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.OK);
    }

    @GetMapping({"/paper/{paperId}"})
    ResponseEntity<BaseResponse<List<CommentResponse>>> getCommentList(@PathVariable Long paperId){
        List<CommentResponse> response = commentService.getCommentList(paperId);
        return new ResponseEntity<>(BaseResponse.setSuccess(response), HttpStatus.OK);
    }


}
