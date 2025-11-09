package com.khoubyari.example.api.rest.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI hotelApiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hotel REST API Example")
                        .version("1.0.0")
                        .description("Spring Boot REST API example for hotels.")
                        .license(new License().name("Siamak License").url("https://github.com/khoubyari")));
    }
}