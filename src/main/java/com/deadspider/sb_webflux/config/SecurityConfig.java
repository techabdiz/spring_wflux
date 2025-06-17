package com.deadspider.sb_webflux.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    
    @Bean
    SecurityWebFilterChain secFilter(ServerHttpSecurity sec) { 
        return sec
            .authorizeExchange(exchange->{
                exchange.pathMatchers(HttpMethod.POST, "/users/create")
                    .permitAll()
                    .anyExchange().authenticated();
            })
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .build();

    }


    @Bean
    public PasswordEncoder pwdEncoder() { 
        return new BCryptPasswordEncoder();
    }
}
