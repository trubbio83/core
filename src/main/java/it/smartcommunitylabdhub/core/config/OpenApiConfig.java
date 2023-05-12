package it.smartcommunitylabdhub.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

// @Configuration
// @EnableWebMvc
//@ComponentScan(basePackages = { "org.springdoc" })
public class OpenApiConfig {
    @Bean
    OpenAPI coreMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Core")
                        .description("{Piattaforma}")
                        .version("1.0"));
    }
}
