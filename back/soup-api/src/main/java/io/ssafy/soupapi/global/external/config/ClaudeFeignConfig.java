package io.ssafy.soupapi.global.external.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ClaudeFeignConfig {

    @Value("${claude.api-key}")
    String apiKey;

    @Value("${claude.anthropic-version}")
    String anthropicVersion;

    @Bean
    public RequestInterceptor claudeHeaderInterceptor() {
        return template -> {
            template.header("Content-Type", "application/json");
            template.header("x-api-key", apiKey);
            template.header("anthropic-version", anthropicVersion);
        };
    }

}
