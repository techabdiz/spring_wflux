package com.deadspider.sb_webflux.controllers;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping(value = "paged", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserDTO> getMethodName(@RequestParam(name = "page", defaultValue = "0") int page, 
                                                    @RequestParam(name = "limit", defaultValue = "10") int limit ) {
        return service.findAllPageable(page, limit);
    }
    


    @GetMapping("{id}")
    //@PreAuthorize(value = "authentication.principal.equals(#id)")
    @PostAuthorize(value = "returnObject.body != null && returnObject.body.id.toString().equals(authentication.principal)")
    public Mono<ResponseEntity<UserDTO>> get(@PathVariable("id") String id) { 
        return service.getById(id)
        .doOnNext(System.out::println)
        .map(data->ResponseEntity.ok().body(data))
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping(produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserDTO> getUsers() { 
        return service.getAllUsers();
    }

    
}
