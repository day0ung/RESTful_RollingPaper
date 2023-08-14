package com.example.restful_api.security.oauth2;

import com.example.restful_api.domain.user.Provider;
import com.example.restful_api.domain.user.Users;
import com.example.restful_api.domain.user.UsersRepository;
import com.example.restful_api.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository usersRepository;

    private static final String GOOGLE = "google";
    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() ");

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Provider provider = getProvider(registrationId);

        OAuth2Attributes extractAttributes = OAuth2Attributes.of(provider, userNameAttributeName, attributes);
        log.info("OAuth2Attributes {}", extractAttributes.getOauth2UserInfo());
        Users users = saveOrUpdate(provider, extractAttributes);

        return new CustomUserPrincipal(users, oAuth2User.getAttributes());
    }

    private Users saveOrUpdate(Provider provider, OAuth2Attributes attributes) {
        Users users = usersRepository.findByEmail(attributes.getOauth2UserInfo().getEmail())
                .orElse(attributes.toEntity(provider, attributes.getOauth2UserInfo()));

        return usersRepository.save(users);
    }


    private Provider getProvider(String registrationId) {
        if(NAVER.equals(registrationId)) {
            return Provider.NAVER;
        }
        if(KAKAO.equals(registrationId)) {
            return Provider.KAKAO;
        }
        if(GOOGLE.equals(registrationId)){
            return Provider.GOOGLE;
        }
        return Provider.GOOGLE;
    }
}
