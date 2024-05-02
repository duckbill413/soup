package io.ssafy.soupapi.global.security.service;

import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class OAuth2RequestProcessorFactory {

    /**
     * @param oAuth2UserRequest OAuth2 리소스 서버의 정보와 유저의 정보가 담긴 Request
     * @return OAuth2 리소스 서버의 소문자 플랫폼 이름 ("kakao", "naver", "google" 등에 따라) (application.yml의 registrationId)
     */
    public OAuth2RequestProcessor createOAuth2Processor(OAuth2UserRequest oAuth2UserRequest) {
        String oAuth2PlatformId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        return switch (oAuth2PlatformId) {
            case "kakao" -> new KakaoOAuth2Processor(oAuth2UserRequest);
            default -> throw new BaseExceptionHandler(ErrorCode.UNSUPPORTED_SOCIAL_PLATFORM);
        };
    }

}
