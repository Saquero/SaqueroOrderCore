package com.saquero.ordercore.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SaqueroOrderCore API")
                        .version("0.1.0")
                        .description("Order lifecycle management backend — Hexagonal Architecture + DDD")
                        .contact(new Contact()
                                .name("Saquero")
                                .url("https://github.com/saquero")));
    }
}