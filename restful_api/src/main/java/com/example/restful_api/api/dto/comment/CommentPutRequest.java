package com.example.restful_api.api.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentPutRequest {

    private String title;

    private String comment;
}
