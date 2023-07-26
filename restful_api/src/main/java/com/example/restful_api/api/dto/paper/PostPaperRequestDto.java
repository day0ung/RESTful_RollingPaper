package com.example.restful_api.api.dto.paper;

import com.example.restful_api.domain.papers.Paper;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class PostPaperRequestDto {

    private String title;
    private String content;


    public Paper toEntity() {
        return Paper.builder()
                .title(title)
                .content(content)
                .build();
    }
}
