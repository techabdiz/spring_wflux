package com.deadspider.sb_webflux.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder encoder;

    public Mono<UserDTO> save(Mono<UserDTO> user) { 
        return user.mapNotNull(User::fromDTO)
            .mapNotNull(u->{
                u.setPassword(encoder.encode(u.getPassword()));
                return u;
            })
            .flatMap(repo::save)
                .mapNotNull(UserDTO::fromEntity);
    }

    public Mono<UserDTO> getById(String id) { 
        return repo.findById(UUID.fromString(id)).map(UserDTO::fromEntity);
    }

    public Flux<UserDTO> findAllPageable(int page, int limit) {
       Pageable pageble = PageRequest.of(page, limit);
       return repo.findAllBy(pageble).map(UserDTO::fromEntity);
    }

    public Flux<UserDTO> getAllUsers() { 
        return repo.findAll()
            .delayElements(Duration.ofMillis(500))
        .map(UserDTO::fromEntity);
    }

}
