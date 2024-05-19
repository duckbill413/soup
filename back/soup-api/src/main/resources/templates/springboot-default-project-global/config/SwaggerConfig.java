package :springboot-project_package.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title(":springboot-project-name")
                .description("""
                        :springboot-project-description
                        """)
                .version("v0.0.1");


        return new OpenAPI()
                .info(info);
    }
}
