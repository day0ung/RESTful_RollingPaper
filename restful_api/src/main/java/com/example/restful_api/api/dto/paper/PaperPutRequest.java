package com.example.restful_api.api.dto.paper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaperPutRequest {
    private String title;
    private String content;
}
