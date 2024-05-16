package io.ssafy.soupapi.global.config;
import io.openvidu.java.client.OpenVidu;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class OpenViduConfig {

    @Value("${openvidu.url}")
    private String openviduUrl;

    @Value("${openvidu.secret}")
    private String openviduSecret;

    @Bean
    public OpenVidu openVidu() {
        return new OpenVidu(openviduUrl, openviduSecret);
    }
}