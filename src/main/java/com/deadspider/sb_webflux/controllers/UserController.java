package com.deadspider.sb_webflux.controllers;

import java.net.URI;
import java.time.Duration;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.deadspider.sb_webflux.models.User;
import com.deadspider.sb_webflux.models.UserDTO;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
public class UserController {

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public  Mono<ResponseEntity<UserDTO>> create (@RequestBody @Valid Mono<User> userMono) {
        return userMono.map(user-> UserDTO.builder()
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .username(user.getUsername())
            .id(UUID.randomUUID().toString()).build())
                .map(dto-> ResponseEntity.created(URI.create("users/get?id="+dto.getId())).body(dto));
        //user.subscribe(data->System.out.println("got user: " + data));
    }


    @GetMapping("{id}")
    public Mono<UserDTO> get(@PathVariable("id") String id) { 
        return Mono.just(UserDTO.builder()
            .id(id)
            .firstName("John")
            .lastName("Doe")
            .email("jdoe@gmail.com")
        .build());
    }

    @GetMapping(produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserDTO> getUsers() { 
    return Flux.fromIterable(
            IntStream.range(0, 10)
                .mapToObj(i->UserDTO.builder()
                    .id(i+"")
                    .firstName("John")
                    .lastName("Doe")
                    .email("jdoe@gmail.com").build()).toList()).delayElements(Duration.ofMillis(500));
    }
    
}
