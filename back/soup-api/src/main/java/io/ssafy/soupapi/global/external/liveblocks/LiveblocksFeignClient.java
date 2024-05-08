package io.ssafy.soupapi.global.external.liveblocks;

import io.ssafy.soupapi.global.external.config.LiveblocksFeignConfig;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.GetLiveblocksUserIdTokenReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.GetLiveblocksUserIdTokenRes;
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
    GetLiveblocksUserIdTokenRes getLiveblocksUserIdToken(
        @RequestBody GetLiveblocksUserIdTokenReq getLiveblocksUserIdTokenReq
    );

}
