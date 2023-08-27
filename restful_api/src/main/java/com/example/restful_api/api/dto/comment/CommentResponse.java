package com.example.restful_api.api.dto.comment;

import com.example.restful_api.domain.commnets.Comment;
import com.example.restful_api.domain.papers.Paper;
import com.example.restful_api.domain.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {

    private Long commentId;

    private User user;

    private String nickName;

    private String comment;

    private Paper paper;


    public CommentResponse(Comment comment){
        this.commentId = comment.getId();
        this.user = comment.getUser();
        this.nickName = comment.getNickName();
        this.comment = comment.getComment();
        this.paper = comment.getPaper();
    }


}
