package com.example.restful_api.security.oauth2.userinfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private Map<String, Object> account;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        this.account = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getEmail() {
        return (String) account.get("email");
    }

    @Override
    public String getName() {
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        if (account == null || profile == null) {
            return null;
        }

        return (String) profile.get("nickname");
    }
}
