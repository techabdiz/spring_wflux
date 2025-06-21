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

    private UserService userService;
    private JwtService jwtService;
    private ReactiveAuthenticationManager authManager;


    public Mono<Map<String, String>> authenticate(String username, String password) { 
        return authManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        ).map(auth -> Map.of("message", jwtService.generateJWT( userService.getEntityByUsername(username)
                        .map(user->user.getId().toString()).block()
                    )
                )
        );
    }
}
