package com.deadspider.sb_webflux.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;


@WebFluxTest(controllers={PublicTest.class})
public class PublicTest {
    @Test
    void testGetRFC() {

    }
}
