package com.skill16.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI studentOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Management API")
                        .description(
                            "SKILL-16 | Full Stack Application Development\n\n" +
                            "This API provides full CRUD operations for managing students. " +
                            "Use the endpoints below to Add, Retrieve, Update, and Delete student records."
                        )
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("FSAD Team")
                                .email("fsad@college.edu"))
                        .license(new License()
                                .name("Academic Use Only")));
    }
}
