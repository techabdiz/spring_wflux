package com.deadspider.sb_webflux.repo;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.deadspider.sb_webflux.models.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository  extends ReactiveCrudRepository<User, UUID>{

    Flux<User> findAllBy(Pageable pageable);
    Mono<User> findByUsername(String username);
}
