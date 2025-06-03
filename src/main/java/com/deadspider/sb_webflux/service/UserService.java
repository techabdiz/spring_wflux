package com.deadspider.sb_webflux.service;

import org.springframework.stereotype.Service;

import com.deadspider.sb_webflux.models.User;
import com.deadspider.sb_webflux.repo.UserRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repo;

    public Mono<User> save(User user) { 
        return repo.save(user);
    }

}
