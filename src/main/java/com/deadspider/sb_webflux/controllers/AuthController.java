package com.deadspider.sb_webflux.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deadspider.sb_webflux.models.Auth;
import com.deadspider.sb_webflux.service.AuthService;
import com.deadspider.sb_webflux.service.JwtService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private AuthService service;


    @PostMapping(value = "login", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody Mono<Auth> auth) { 
        return auth
            .flatMap(val -> service
                .authenticate(val.getUsername(), val.getPassword()))
                .map(val -> ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer ") 
                    .body(val))
                    .onErrorReturn(BadCredentialsException.class, 
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "invalid credentails")));
    }


}
