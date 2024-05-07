package io.ssafy.soupapi.external.claude;

import io.ssafy.soupapi.external.config.ClaudeFeignConfig;
import io.ssafy.soupapi.external.claude.dto.CreateClaudeMessageReq;
import io.ssafy.soupapi.external.claude.dto.CreateClaudeMessageRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name="claudeFeignClient",
    url="${feign.claude.url}",
    configuration = { ClaudeFeignConfig.class }
)
public interface ClaudeFeignClient {

    @PostMapping(value = "/v1/messages", consumes = "application/json")
    CreateClaudeMessageRes getClaudeMessage(
        @RequestBody CreateClaudeMessageReq createClaudeMessageReq
    );

}
