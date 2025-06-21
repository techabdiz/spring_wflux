package com.deadspider.sb_webflux.filters;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.deadspider.sb_webflux.service.JwtService;

import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class JwtAuthFilter implements WebFilter {

    private JwtService service;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) { 
            String token = authHeader.substring(7);
            System.out.println("extracted token: " + token);
            if(!Objects.isNull(token)) { 
                return validate(token)
                    .flatMap(valid -> {
                        if ( valid ){ 
                            return chain
                                .filter(exchange)
                                .contextWrite(
                                    ReactiveSecurityContextHolder
                                        .withAuthentication(
                                            new UsernamePasswordAuthenticationToken(
                                                service.getSubject(token), 
                                                null, 
                                                Collections.emptyList())
                                        )
                                );
                        } 
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Boolean> validate(String token) { 
        return service.isValid(token);
    }

}
