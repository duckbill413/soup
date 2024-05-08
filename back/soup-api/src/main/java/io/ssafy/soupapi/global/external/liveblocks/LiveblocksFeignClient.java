package io.ssafy.soupapi.global.external.liveblocks;

import io.ssafy.soupapi.global.external.config.LiveblocksFeignConfig;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.GetUserIdTokenReq;
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

    //Get ID token with secret key
    @PostMapping(value = "/identify-user", consumes = "application/json")
    GetUserIdTokenRes getLiveblocksUserIdToken(
        @RequestBody GetUserIdTokenReq getUserIdTokenReq
    );

}
