package com.deadspider.sb_webflux.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.deadspider.sb_webflux.models.User;
import com.deadspider.sb_webflux.models.UserDTO;
import com.deadspider.sb_webflux.repo.UserRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repo;

    @Mock
    private PasswordEncoder encoder;
    
    @InjectMocks
    private UserService service;

    @BeforeEach
    void setup() { 
      //  service = new UserService(repo, encoder);
    }

    @Test
    void testSaveUser_WithValidRequest_CreatedUserDetails() {

        User obj = User.builder()
        .id(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"))
            .firstName("John")
            .lastName("Doe")
            .email("john@gmail")
            .password("some password")
        .build();
        
        Mono<User> user = Mono.just(obj);

        when(repo.save(any()))
            .thenReturn(user);
        
        when(encoder.encode(any()))
            .thenReturn("some password");

        Mono<UserDTO> retval = service.save(Mono.just(UserDTO.fromEntity(obj)));

        StepVerifier.create(retval)
            .expectNextMatches(rest -> rest.getId().equals(obj.getId().toString()))
            //.expectNextMatches(rest -> rest.getFirstName().equals("Jerry")) // this fill fail here
            .verifyComplete();

    }

}
