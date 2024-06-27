package com.example.gateway_service.config;

import com.example.gateway_service.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    //private final EurekaClient discoveryClient;
    private final AuthenticationFilter authFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user", r -> r.path("/user/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8080"))
                .route("user-admin", r -> r.path("/user/admin/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8080"))
                .route("movie", r -> r.path("/movie/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8081"))
                .route("movie-admin", r -> r.path("/movie/admin/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8081"))
                .route("review", r -> r.path("/review/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8082"))
                .route("cast", r -> r.path("/cast/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8083"))
                .route("cast-admin", r -> r.path("/cast/admin/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8083"))
                .build();
    }

    /*public String serviceUrl(String applicationName) {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(applicationName, false);
        return instance.getHomePageUrl();
    }*/
}
