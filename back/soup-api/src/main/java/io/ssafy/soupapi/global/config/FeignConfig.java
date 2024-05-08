package io.ssafy.soupapi.global.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration // 모든 FeignClient에 설정이 적용된다
@EnableFeignClients(basePackages = "io.ssafy.soupapi") // package name
@Import(FeignClientsConfiguration.class)
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // log 레벨 설정
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 2000, 3);
    }

}
