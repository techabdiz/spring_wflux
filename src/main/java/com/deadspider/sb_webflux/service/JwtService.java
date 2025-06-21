package com.deadspider.sb_webflux.service;

import java.time.Instant;
import java.util.Date;


import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Service
public class JwtService {

    private final String secret = 
        "0a1152d6550ce52e0780aec7de68fbca66e360c148bbe7584c4842e1345aa04d";

    public String generateJWT(String subject) { 
        return Jwts.builder().subject(subject)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(
                        Instant.now().plusSeconds(60*60)))
                        .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                        .compact();
    }

    public Mono<Boolean> isValid(String token) { 
        return Mono.just(token)
            .map(this::parseJwtToken).map(claims -> 
                !claims.getExpiration()
                .before(new Date())
            
            );
    }

    public String getSubject(String token ) { 
        return parseJwtToken(token).getSubject();
    }

    private Claims parseJwtToken(String token) { 
        return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }
}
