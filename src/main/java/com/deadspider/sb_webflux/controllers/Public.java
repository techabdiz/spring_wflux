package com.deadspider.sb_webflux.controllers;


import java.util.Map;
import java.util.function.Predicate;

import javax.print.attribute.standard.Media;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

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

    @GetMapping(value = "get_rfc/{rfc}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> getRFC(@PathVariable("rfc") String rfc, 
            @RequestParam(name = "size", defaultValue = "100" ) Long size) { 
        return WebClient.create(String.format("https://www.ietf.org/rfc/%s.txt", rfc))
            .get()
            .retrieve()
            .bodyToFlux(String.class)
            .filter(Predicate.not(String::isBlank))
            .take(size)
            .reduce((current, accumulator)->accumulator+"\r\n"+current);
    }
}
