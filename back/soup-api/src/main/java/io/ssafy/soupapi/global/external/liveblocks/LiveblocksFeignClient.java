package io.ssafy.soupapi.global.external.liveblocks;

import io.ssafy.soupapi.global.external.config.LiveblocksFeignConfig;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.CreateRoomReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.GetUserIdTokenReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.UpdateRoomReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.ChangeRoomRes;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.GetUserIdTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
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

    // create room
    @PostMapping(value = "/rooms", consumes = "application/json")
    ChangeRoomRes createRoom(
        @RequestBody CreateRoomReq createRoomReq
    );

    // update room
    @PostMapping(value = "/rooms/{roomId}", consumes = "application/json")
    ChangeRoomRes updateRoom(
        @PathVariable String roomId,
        @RequestBody UpdateRoomReq updateRoomReq
    );

}
