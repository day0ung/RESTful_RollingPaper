package com.example.restful_api.service;

import com.example.restful_api.api.dto.comment.CommentPostRequest;
import com.example.restful_api.api.dto.comment.CommentPutRequest;
import com.example.restful_api.api.dto.comment.CommentResponse;
import com.example.restful_api.domain.commnets.Comment;
import com.example.restful_api.domain.commnets.CommentRepository;
import com.example.restful_api.domain.papers.Paper;
import com.example.restful_api.domain.papers.PaperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService extends BaseService{

    private final CommentRepository commentRepository;

    private final PaperRepository paperRepository;

    public CommentResponse createComment(Long paperId, CommentPostRequest request) {
        Paper paper = verify(paperId, paperRepository, Paper.class);
        Comment comment = commentRepository.save(request.toEntity(paper));
        return new CommentResponse(comment);
    }

    public CommentResponse updateComment(Long commentId, CommentPutRequest request) {
        Comment comment = verify(commentId, commentRepository, Comment.class);

        if (!request.getComment().isEmpty()) comment.updateComment(request.getComment());
        if (!request.getNickName().isEmpty()) comment.updateName(request.getNickName());

        return new CommentResponse(comment);

    }

    public CommentResponse deleteComment(Long commentId) {
        Comment comment = verify(commentId, commentRepository, Comment.class);
        commentRepository.deleteById(comment.getId());
        return new CommentResponse(comment);
    }

    public CommentResponse getComment(Long commentId) {
        Comment comment = verify(commentId, commentRepository, Comment.class);

        return new CommentResponse(comment);
    }

    public List<CommentResponse> getCommentList(Long paperId) {
        Paper paper = verify(paperId, paperRepository, Paper.class);

        List<CommentResponse> commentResponseList = paper.getCommentList()
                .stream().map( comment -> new CommentResponse(comment))
                .collect(Collectors.toList());

        return commentResponseList;
    }
}
