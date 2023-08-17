package com.example.restful_api.api.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentPutRequest {

    private String comment;

    private String nickName;
}
