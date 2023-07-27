package com.example.restful_api.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName="set")
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> BaseResponse<T> setSuccess(T data) {
        return BaseResponse.set(BaseResponseStatus.SUCCESS.getCode(), BaseResponseStatus.SUCCESS.getMessage(), data);
    }
    public static <T> BaseResponse<T> setStatus(BaseResponseStatus status ) {
        return BaseResponse.set(status.getCode(), status.getMessage(), null);
    }

}
