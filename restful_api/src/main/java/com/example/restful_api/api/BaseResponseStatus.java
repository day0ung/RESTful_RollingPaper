package com.example.restful_api.api;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    SUCCESS(1000, "요청 성공.");

    private final int code;
    private final String message;

    private BaseResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

