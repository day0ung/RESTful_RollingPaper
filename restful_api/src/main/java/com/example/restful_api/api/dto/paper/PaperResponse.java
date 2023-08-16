package com.example.restful_api.api.dto.paper;

import com.example.restful_api.domain.commnets.Comment;
import com.example.restful_api.domain.papers.Paper;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaperResponse {
    private Long paperId;

    private Long userId;

    private String title;

    private String content;

    private List<Comment> commentList;


    public PaperResponse(Paper paper){
        this.paperId = paper.getId();
        this.userId = paper.getUser().getId();
        this.title = paper.getTitle();
        this.content = paper.getContent();
        this.commentList = paper.getCommentList();
    }
}
