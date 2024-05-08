package io.ssafy.soupapi.global.external.liveblocks.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record GetUserIdTokenReq(
    String userId,
    List<String> groupIds,
    UserInfo userInfo
) {

    @Builder
    public record UserInfo(
        String name,
        String email,
        String profileImgUrl
    ) {
    }

}
