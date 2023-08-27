package com.example.restful_api.api.dto.user;


import com.example.restful_api.domain.commnets.Comment;
import com.example.restful_api.domain.papers.Paper;
import com.example.restful_api.domain.user.Role;
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
public class UserResponse {

    private Long id;

    private String name;

    private Role role;

    private List<Paper> paperList;

    private List<Comment> commentList;


    public UserResponse(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.role = user.getRole();
        this.paperList = user.getPapers();
        this.commentList = user.getComments();

    }
}
