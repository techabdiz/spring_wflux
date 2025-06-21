package com.deadspider.sb_webflux.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;

import com.deadspider.sb_webflux.filters.JwtAuthFilter;
import com.deadspider.sb_webflux.service.JwtService;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    
    @Bean
    SecurityWebFilterChain secFilter(ServerHttpSecurity sec, 
            ReactiveAuthenticationManager authManager,
            JwtService jwtService) { 

        JwtAuthFilter jwtFilter = new JwtAuthFilter(jwtService);
        return sec
            .authorizeExchange(exchange->{
                exchange.pathMatchers(HttpMethod.POST, 
                "/users/create", 
                                "/auth/login")
                    .permitAll()
                    .pathMatchers(HttpMethod.GET, "/public/**")
                    .permitAll()
                    .anyExchange().authenticated();
            })
            .httpBasic(HttpBasicSpec::disable)
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authenticationManager(authManager)
            .addFilterAfter(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .securityContextRepository(
                NoOpServerSecurityContextRepository.getInstance())
            .build();

    }


    @Bean
    public PasswordEncoder pwdEncoder() { 
        return new BCryptPasswordEncoder();
    }
}
