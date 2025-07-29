package com.mosesidowu.api_gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    private static final List<String> PUBLIC_ROUTES = List.of(
            "/users/register-user",
            "/users/login"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        System.out.println("REQUEST PATH: " + path);

        if (PUBLIC_ROUTES.stream().anyMatch(path::startsWith)) {
            System.out.println("Public route, skipping JWT check.");
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Invalid or missing Authorization header.");
            return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        System.out.println("Extracted token: " + token);

        if (!jwtUtil.isTokenValid(token)) {
            System.out.println("JWT Token is invalid.");
            return onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
        }

        // Optional: attach userId to header
        String userId = jwtUtil.extractUserId(token);
        exchange = exchange.mutate()
                .request(r -> r.headers(h -> h.set("X-User-Id", userId)))
                .build();

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String missingOrInvalidAuthorizationHeader, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        String errorResponse = String.format("{\"error\": \"%s\"}", missingOrInvalidAuthorizationHeader);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorResponse.getBytes())));
    }

    @Override
    public int getOrder() {
        return -1; // High precedence
    }
}
