package com.example.restful_api.api.dto.comment;

import com.example.restful_api.domain.commnets.Comment;
import com.example.restful_api.domain.papers.Paper;
import com.example.restful_api.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentPostRequest {

    private String nickName;

    private String comment;

    public Comment toEntity(Paper paper, User user){
        return Comment.builder()
                .nickName(nickName)
                .comment(comment)
                .user(user)
                .paper(paper)
                .build();
    }
}
