package com.example.restful_api.api.dto.paper;

import com.example.restful_api.domain.papers.Paper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaperPostRequest {

    private String title;
    private String content;


    public Paper toEntity() {
        return Paper.builder()
                .title(title)
                .content(content)
                .build();
    }
}
