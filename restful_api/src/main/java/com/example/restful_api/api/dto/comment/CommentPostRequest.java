package com.example.restful_api.api.dto.comment;

import com.example.restful_api.domain.commnets.Comment;
import com.example.restful_api.domain.papers.Paper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentPostRequest {

    private String title;

    private String comment;

    public Comment toEntity(Paper paper){
        return Comment.builder()
                .title(title)
                .comment(comment)
                .paper(paper)
                .build();
    }
}
