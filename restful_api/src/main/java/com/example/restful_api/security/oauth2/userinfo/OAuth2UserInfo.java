package com.example.restful_api.security.oauth2.userinfo;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getProviderId(); // OAuth2 로그인 시 키(PK)가 되는 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"

    public abstract String getEmail();

    public abstract String getName();
}
