package com.example.restful_api.api.dto.paper;

import com.example.restful_api.domain.papers.Paper;
import com.example.restful_api.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaperPostRequest {

    private String title;
    private String content;


    public Paper toEntity(User user) {
        return Paper.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
