package io.ssafy.soupapi.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Soup")
                .description("""
                        <h2>Start Organization, Upgrade Project!</h1>
                        <p style="text-align: right">by A201</p>
                        <p><b>당신!</b>을 위한 <b>accessToken</b> (유효 기간: 약 365일) :</p>
                        <p>eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImdlbmVyYXRlRGF0ZSI6MTcxNDYzNTQ2Mjk4Nn0.eyJpc3MiOiJjdXRlLXNvdXAiLCJleHAiOjMxNzE0NjM1NDYyLCJzdWIiOiI2ZDA0NThlYy1iMjZmLTRiNjAtYWYzYi1hN2ZlMWUyMzYxY2MiLCJyb2xlcyI6WyJST0xFX1VTRVIiXX0.BUq8qirwzQS76BttwaqLuHYPhT9eVtbFunevsk1Z8wE</p>
                        """)
                .contact(new Contact().name("soup").url(""))
                .version("v0.0.1");

        var webDomainServer = new Server().description("Default Domain Server").url("https://back.so-up.store");
        var webSsafyServer = new Server().description("Web SSAFY Server").url("https://k10a201.p.ssafy.io");
        var localServer = new Server().description("local server").url("http://localhost:8080");

//        return new OpenAPI()
//                .info(info);

        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
        );

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .addServersItem(webDomainServer)
                .addServersItem(webSsafyServer)
                .addServersItem(localServer)
                .components(components);
    }
}
