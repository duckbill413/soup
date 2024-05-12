package io.ssafy.soupapi.global.external.liveblocks.dao;

import io.ssafy.soupapi.global.external.config.LiveblocksFeignConfig;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.CreateRoomReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.GetUserIdTokenReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.UpdateRoomReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.ChangeRoomRes;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.GetUserIdTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@FeignClient(
        name="liveblocksFeignClient",
        url="${liveblocks.base-url}",
        configuration = { LiveblocksFeignConfig.class }
)
public interface LbFeignClient {

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
    // roomId가 /project/{projectId}/outline 같은 형식인데, 이를 PathVariable로 받으면 %2F -> / 로 자동 인코딩이 돼버린다
    // 그래서 (일단) 이렇게 하드 코딩하는 수밖에..
    @PostMapping(value = "/rooms/%2Fproject%2F{projectId}%2F{stepName}", consumes = "application/json")
    ChangeRoomRes updateRoom(
        @PathVariable("projectId") String projectId,
        @PathVariable("stepName") String stepName,
        @RequestBody UpdateRoomReq updateRoomReq
    );

    @GetMapping(value = "/rooms/%2Fproject%2F{projectId}%2F{stepName}/storage?format=json", consumes = "application/json")
    HashMap<String, Object> getRoomStorageDocument(
            @PathVariable("projectId") String projectId,
            @PathVariable("stepName") String stepName
    );

}
