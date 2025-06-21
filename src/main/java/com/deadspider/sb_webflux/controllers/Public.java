package com.deadspider.sb_webflux.controllers;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("public")
public class Public {

    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    @GetMapping(value = "broadcast")
    public Mono<ResponseEntity<Map<String, String>>> 
        broadcast(@RequestParam("message") String message) { 
            sink.tryEmitNext(message);
            return Mono.just(ResponseEntity.ok().body(Map.of("message", "sent successfully")));
    }

    @GetMapping(value = "emit", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> emit(){ 
        System.out.println("emmitting data ... 1");
        return sink.asFlux();
        
    }
}
