package com.deadspider.sb_webflux.service;

import java.util.Map;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AuthService {

    private ReactiveAuthenticationManager authManager;


    public Mono<Map<String, String>> authenticate(String username, String password) { 
        return authManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        ).map(auth -> Map.of("message", "login was successfull !"));
    }
}
