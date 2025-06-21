package com.deadspider.sb_webflux.config;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobExceptionHandler {

    @ExceptionHandler(exception = DuplicateKeyException.class)
    public Mono<ErrorResponse> duplicatePrimaryKey(DuplicateKeyException e) { 
        return Mono.just(ErrorResponse
            .builder(e, HttpStatus.CONFLICT,"GlobExceptionHandler: object already exists with id")
            .build());
    }

    @ExceptionHandler(exception = WebExchangeBindException.class)
    public Mono<ErrorResponse> handleBeanValidations(WebExchangeBindException ex) { 
        return Mono.just(
            ErrorResponse.builder(ex, HttpStatus.INTERNAL_SERVER_ERROR, 
            ex.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", ")))
        .build());
    }

    @ExceptionHandler(exception = Exception.class)
    public Mono<ErrorResponse> generalHandler(Exception ex) { 
        return Mono.just(ErrorResponse.builder(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()).build());
    }

}
