package io.ssafy.soupapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@OpenAPIDefinition(
        servers = {
                @Server(url="https://be-api.so-up.store", description="Default Server url")
        }
)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SoupApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoupApiApplication.class, args);
    }

}
