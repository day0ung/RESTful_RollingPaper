package com.example.restful_api.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor(staticName="set")
public class BaseResponse<T> {
    private int httpCode;
    private String message;
    private T data;

    public static <T> BaseResponse<T> setSuccess(T data) {
        return BaseResponse.set(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }
    public static <T> BaseResponse<T> set(HttpStatus httpStatus, T data ) {
        return BaseResponse.set(httpStatus.value(), httpStatus.getReasonPhrase(), data);
    }

}
