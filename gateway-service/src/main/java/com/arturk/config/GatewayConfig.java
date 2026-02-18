package com.arturk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return route("product")
                .route(RequestPredicates.path("/api/product"), http("http://localhost:4010"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return route("order")
                .route(RequestPredicates.path("/api/order"), http("http://localhost:4020"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        return route("inventory")
                .route(RequestPredicates.path("/api/inventory"), http("http://localhost:4030"))
                .build();
    }

}
