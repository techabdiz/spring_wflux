package com.deadspider.sb_webflux.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deadspider.sb_webflux.models.Auth;
import com.deadspider.sb_webflux.service.AuthService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("login")
    public Mono<Map<String, String>> login(@RequestBody Mono<Auth> auth) { 
        return null;//Mono.just(ResponseEntity.ok().build()));
    }


}
