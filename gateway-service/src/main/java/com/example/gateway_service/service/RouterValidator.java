package com.example.gateway_service.service;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {
    public static final List<String> openEndpoints = List.of(
            "/user/authentication/v1/register",
            "/user/authentication/v1/login"
    );

    public Predicate<ServerHttpRequest> isOpen =
            request -> openEndpoints.stream()
                    .anyMatch(uri -> request
                            .getURI()
                            .getPath()
                            .contains(uri));
}
