package com.deadspider.sb_webflux.controllers;

import java.net.URI;
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
import com.deadspider.sb_webflux.models.UserDTO;
import com.deadspider.sb_webflux.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private UserService service;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public  Mono<ResponseEntity<UserDTO>> create (@RequestBody @Valid Mono<UserDTO> userMono) {
        return service.save(userMono)
                .map(dto-> ResponseEntity.created(URI.create("users/get?id="+dto.getId())).body(dto));
        //user.subscribe(data->System.out.println("got user: " + data));
    }


    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDTO>> get(@PathVariable("id") String id) { 
        return service.getById(id).map(data->ResponseEntity.ok().body(data));
    }

    @GetMapping(produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserDTO> getUsers() { 
        return service.getAllUsers();
    }
    
}
