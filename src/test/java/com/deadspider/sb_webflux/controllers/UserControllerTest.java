package com.deadspider.sb_webflux.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.deadspider.sb_webflux.config.TestSecurityConfig;
import com.deadspider.sb_webflux.models.UserDTO;
import com.deadspider.sb_webflux.service.UserService;

import reactor.core.publisher.Mono;

@WebFluxTest(UserController.class)
@Import(
    TestSecurityConfig.class
)

public class UserControllerTest {

    @MockitoBean
    private UserService service;

    @Autowired
    private WebTestClient testClient;

    @Test
    void testCreate_withValidRequest_returnsCreatedStatusAndUserDetails() {
        UserDTO obj = UserDTO.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john@gmail")
            .password("some password")
            .id("12346")
        .build();
        Mono<UserDTO> user = Mono.just(obj);
        when(service.save(any()))
            .thenReturn(user);

        testClient
            .post()
            .uri("/users/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(obj)
            .exchange()
        .expectStatus().isCreated()
        .expectBody(UserDTO.class)
        .value(res -> { 
            assertNotNull(res);
        });
            
        verify(service, times(1)).save(any());
    }


}
