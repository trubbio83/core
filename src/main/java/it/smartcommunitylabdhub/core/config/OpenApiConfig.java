package it.smartcommunitylabdhub.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@ComponentScan(basePackages = { "org.springdoc" })
public class OpenApiConfig {
    @Bean
    OpenAPI coreMicroserviceOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info().title("Core")
                        .description("{Piattaforma}")
                        .version("1.0"));

        return openAPI;
    }
}
