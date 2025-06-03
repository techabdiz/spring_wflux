package com.deadspider.sb_webflux.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ReactController {

    @GetMapping("demo")
    public Flux<String> demo() { 
        return getItems();
    }

    public Flux<String> getItems() { 
        return Flux.just("Apple", "Mango", "Pineapple");
    }


    public static void main(String[] args) {
        new ReactController().demo()
        .log()
        .subscribe(item->{
            System.out.println("recieved:  " + item);
        });
    }


}
