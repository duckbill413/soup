package io.ssafy.soupbuild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SoupBuildApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoupBuildApplication.class, args);
    }

}
