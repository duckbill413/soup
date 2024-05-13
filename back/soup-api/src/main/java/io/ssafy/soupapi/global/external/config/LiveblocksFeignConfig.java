package io.ssafy.soupapi.global.external.config;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Slf4j
public class LiveblocksFeignConfig {

    @Value("${liveblocks.secret-key}")
    String secretKey;

    @Bean
    public RequestInterceptor liveblocksHeaderInterceptor() {
        return template -> {
            template.header("Authorization", "Bearer " + secretKey);
        };
    }

}
