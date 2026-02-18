package com.arturk.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI inventoryServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Inventory Service API")
                        .description("This is the REST API for Inventory Service")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")))
                .servers(List.of(
                        new Server().url("https://inventory-service-dummy-url.com").description("Dummy server")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer to the Inventory Service Wiki Documentation")
                        .url("https://inventory-service-dummy-url.com/docs"));
    }
}
