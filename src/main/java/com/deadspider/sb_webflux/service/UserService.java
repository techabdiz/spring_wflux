package com.deadspider.sb_webflux.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.deadspider.sb_webflux.models.User;
import com.deadspider.sb_webflux.models.UserDTO;
import com.deadspider.sb_webflux.repo.UserRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repo;

    public Mono<UserDTO> save(Mono<UserDTO> user) { 
        return user.mapNotNull(User::fromDTO)
            .flatMap(repo::save)
                .mapNotNull(UserDTO::fromEntity);
    }

    public Mono<UserDTO> getById(String id) { 
        return repo.findById(UUID.fromString(id)).map(UserDTO::fromEntity);
    }

    public Flux<UserDTO> getAllUsers() { 
        return repo.findAll()
            .delayElements(Duration.ofMillis(500))
        .map(UserDTO::fromEntity);
    }

}
