package com.project.task.manager.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerApi {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manager API")
                        .version("1.0")
                        .description("""
                                REST API для Task Manager.
                                
                                Functions:
                                - User CRUD + JWT auth
                                - Task CRUD + pagination
                                - Comments + cascade delete
                                - Admin panel
                                """)
                        .contact(new Contact()
                                .name("Ymir")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT token from /auth/login")))
                        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
