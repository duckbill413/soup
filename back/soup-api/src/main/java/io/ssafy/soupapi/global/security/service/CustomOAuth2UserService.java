package io.ssafy.soupapi.global.security.service;

import io.ssafy.soupapi.domain.member.dao.MemberRepository;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.member.entity.SocialType;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuth2RequestProcessorFactory oAuth2RequestProcessorFactory;
    private final MemberRepository memberRepository;

    @Transactional // memberRepository로 DB 접근하니까
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2RequestProcessor oAuth2Parser = oAuth2RequestProcessorFactory.createOAuth2Processor(oAuth2UserRequest);
        Map<String, Object> stringObjectMap = oAuth2Parser.getSocialUserAttributes();
        Object socialIdObj = stringObjectMap.get(oAuth2Parser.loadUserNameAttributeName());

        hasSocialAccountInfo(socialIdObj, stringObjectMap);

        return getSecurityDto(socialIdObj, stringObjectMap);
    }

    private void hasSocialAccountInfo(Object socialIdObj, Map<String, Object> socialInfoMap) {
        Object platformObj = socialInfoMap.get("platform");
        Object emailObj = socialInfoMap.get("email");

        // 소셜 회원 정보가 있는지 확인
        if (Objects.isNull(platformObj) || Objects.isNull(socialIdObj) || Objects.isNull(emailObj)) {
            throw new BaseExceptionHandler(ErrorCode.NO_SOCIAL_USER_ATTRIBUTES);
        }
    }

    private UserSecurityDTO getSecurityDto(Object socialIdObj, Map<String, Object> socialInfoMap) {
        String platform = (String) socialInfoMap.get("platform");
        SocialType socialType = SocialType.fromString(platform);
        String socialId = (String) socialIdObj;

        // SOUP 서비스 회원인지 확인
        Optional<Member> member = memberRepository.findBySocialTypeAndSocialId(socialType, socialId);

        // 멤버 존재 O -> 로그인 처리
        if (member.isPresent()) {
            return UserSecurityDTO.getUserSecurityDTO(member.get(), socialInfoMap);
        }

        // 멤버 존재 X (비회원) -> 회원 가입
        return UserSecurityDTO.getUserSecurityDTO(registerMember(socialIdObj, socialInfoMap));
    }

    // 신규 회원 등록
    public Member registerMember(Object socialIdObj, Map<String, Object> socialInfoMap) {
        String platform = (String) socialInfoMap.get("platform");
        SocialType socialType = SocialType.fromString(platform);
        String socialId = (String) socialIdObj;

        String email = (String) socialInfoMap.get("email");
        String nickname = (String) socialInfoMap.get("nickname");
        String profileImageUrl = (String) socialInfoMap.get("profileImageUrl");

        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .socialId(socialId)
                .socialType(socialType)
                .build();

        return memberRepository.save(member);
    }

}
