package io.ssafy.soupapi.global.security.service;

import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Map;

@Slf4j
public class KakaoOAuth2Processor extends OAuth2RequestProcessor {

    public KakaoOAuth2Processor(OAuth2UserRequest oAuth2UserRequest) {
        super(oAuth2UserRequest);
    }

    /**
     * @param userAttributes OAuth2User.getAttributes()
     * @return kakao_account map을 반환
     */
    private Map<?, ?> getMapKakaoAccount(Map<?, ?> userAttributes) {
        Object mapKakaoAccount = userAttributes.get("kakao_account");
        if (mapKakaoAccount instanceof Map<?, ?>)
            return (Map<?, ?>) mapKakaoAccount;
        throw new BaseExceptionHandler(ErrorCode.NO_SOCIAL_USER_ATTRIBUTES);
    }

    /**
     * @param userAttributes OAuth2User.getAttributes()
     * @return kakao_account 안의 profile map을 반환
     */
    private Map<?, ?> getMapProfile(Map<?, ?> userAttributes) {
        Object mapProfile = getMapKakaoAccount(userAttributes).get("profile");
        if (mapProfile instanceof Map<?, ?>)
            return (Map<?, ?>) mapProfile;
        throw new BaseExceptionHandler(ErrorCode.NO_SOCIAL_USER_ATTRIBUTES);
    }

    @Override
    String getEmail(Map<String, Object> userAttributes) {
        Object email = getMapKakaoAccount(userAttributes).get("email");
        if (email instanceof String) return (String) email;
        return null;
    }

    @Override
    String getNickname(Map<String, Object> userAttributes) {
        Object nickname = getMapProfile(userAttributes).get("nickname");
        if (nickname instanceof String) return (String) nickname;
        return null;
    }

    @Override
    String getProfileImageUrl(Map<String, Object> userAttributes) {
        Object profileImageUrl = getMapProfile(userAttributes).get("profile_image_url");
        if (profileImageUrl instanceof String) return (String) profileImageUrl;
        return null;
    }

}
