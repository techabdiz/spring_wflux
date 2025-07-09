package com.deadspider.sb_webflux.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityWebFilterChain secConfig(ServerHttpSecurity sec) { 
        return sec.csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(http->{ 
                http.anyExchange().permitAll();
            })
        .build();
    }

}
