package com.deadspider.sb_webflux.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

    @Bean
    public ReactiveAuthenticationManager authManager(ReactiveUserDetailsService service, 
        PasswordEncoder encoder) { 

        UserDetailsRepositoryReactiveAuthenticationManager authMan = 
            new UserDetailsRepositoryReactiveAuthenticationManager(service);
        authMan.setPasswordEncoder(encoder);
        return authMan;

    }

}
