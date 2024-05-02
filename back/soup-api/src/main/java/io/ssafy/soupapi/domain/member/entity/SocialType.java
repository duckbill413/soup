package io.ssafy.soupapi.domain.member.entity;

import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;

public enum SocialType {
    KAKAO, NAVER, GOOGLE;

    public static SocialType fromString(String value) {
        for (SocialType socialType : SocialType.values()) {
            if (socialType.name().equalsIgnoreCase(value)) return socialType;
        }

        throw new BaseExceptionHandler(ErrorCode.UNSUPPORTED_SOCIAL_PLATFORM);
    }
}
