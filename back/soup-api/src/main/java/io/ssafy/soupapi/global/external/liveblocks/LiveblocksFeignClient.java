package io.ssafy.soupapi.global.external.liveblocks;

import io.ssafy.soupapi.global.external.config.LiveblocksFeignConfig;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.CreateRoomReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.GetUserIdTokenReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.CreateRoomRes;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.GetUserIdTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name="liveblocksFeignClient",
        url="${liveblocks.base-url}",
        configuration = { LiveblocksFeignConfig.class }
)
public interface LiveblocksFeignClient {

    // user ID token 발급 받기
    @PostMapping(value = "/identify-user", consumes = "application/json")
    GetUserIdTokenRes getLiveblocksUserIdToken(
        @RequestBody GetUserIdTokenReq getUserIdTokenReq
    );

    // room 생성
    @PostMapping(value = "/rooms", consumes = "application/json")
    CreateRoomRes createRoom(
        @RequestBody CreateRoomReq createRoomReq
    );

}
