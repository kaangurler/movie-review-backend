package com.example.gateway_service.filter;

import com.example.gateway_service.service.JwtService;
import com.example.gateway_service.service.RouterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {
    private final RouterValidator routerValidator;
    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        RequestPath path =  request.getPath();
        System.out.println(path);
        try {
            if (routerValidator.isOpen.test(request)) {
                return chain.filter(exchange);
            }
            String token = request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
            String authority = jwtService.extractAuthorities(token);
            System.out.println("JWT: " + token);
            if (!jwtService.extractUsername(token).isBlank()) {
                if (path.toString().matches("(.*)/admin/(.*)") && authority.equals("ADMIN")) {
                    return chain.filter(exchange);
                }
                if (!path.toString().matches("(.*)/admin/(.*)")) {
                    return chain.filter(exchange);
                }
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
