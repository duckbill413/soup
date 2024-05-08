package io.ssafy.soupapi.global.external.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

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
