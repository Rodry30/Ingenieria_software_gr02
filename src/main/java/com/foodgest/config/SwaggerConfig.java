package com.foodgest.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("01 Autenticacion y usuarios")
                .pathsToMatch("/api/auth/**", "/api/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi marketplaceApi() {
        return GroupedOpenApi.builder()
                .group("02 Marketplace")
                .pathsToMatch("/api/marketplace/**")
                .build();
    }

    @Bean
    public GroupedOpenApi pedidosApi() {
        return GroupedOpenApi.builder()
                .group("03 Pedidos y logistica")
                .pathsToMatch("/api/pedidos/**", "/api/logistica/**")
                .build();
    }

    @Bean
    public GroupedOpenApi comunicacionesApi() {
        return GroupedOpenApi.builder()
                .group("04 Comunicaciones")
                .pathsToMatch("/api/comunicaciones/**", "/api/reputacion/**")
                .build();
    }

    @Bean
    public GroupedOpenApi subastasApi() {
        return GroupedOpenApi.builder()
                .group("05 Subastas y suscripciones")
                .pathsToMatch("/api/subastas/**", "/api/suscripciones/**")
                .build();
    }

    @Bean
    public GroupedOpenApi catalogoPerfilesApi() {
        return GroupedOpenApi.builder()
                .group("06 Catalogo y perfiles")
                .pathsToMatch("/api/catalogo/**", "/api/perfiles/**")
                .build();
    }
}

