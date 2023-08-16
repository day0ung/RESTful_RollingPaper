package com.example.restful_api.security.oauth2;

import com.example.restful_api.domain.user.Provider;
import com.example.restful_api.domain.user.Role;
import com.example.restful_api.domain.user.User;
import com.example.restful_api.security.oauth2.userinfo.KakaoOAuth2UserInfo;
import com.example.restful_api.security.oauth2.userinfo.NaverOAuth2UserInfo;
import com.example.restful_api.security.oauth2.userinfo.OAuth2UserInfo;
import com.example.restful_api.security.oauth2.userinfo.GoogleOAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
public class OAuth2Attributes {

    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oauth2UserInfo;// 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

    @Builder
    public OAuth2Attributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuth2Attributes of(Provider provider,
                                      String userNameAttributeName, Map<String, Object> attributes) {

        if (provider == Provider.NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        }
        if (provider == Provider.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuth2Attributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    public User toEntity(Provider provider, OAuth2UserInfo oauth2UserInfo) {
        return User.builder()
                .name(oauth2UserInfo.getName())
                .email(oauth2UserInfo.getEmail())
                .password(oauth2UserInfo.getEmail())
                .provider(provider)
                .providerId(oauth2UserInfo.getProviderId())
                .role(Role.USER)
                .build();
    }
}
