package com.deadspider.sb_webflux.repo;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.deadspider.sb_webflux.models.User;

public interface UserRepository  extends ReactiveCrudRepository<User, UUID>{

}
