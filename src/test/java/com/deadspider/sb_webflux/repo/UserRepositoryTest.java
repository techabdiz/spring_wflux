package com.deadspider.sb_webflux.repo;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;

import com.deadspider.sb_webflux.models.User;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataR2dbcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    private DatabaseClient client;

    @Autowired
    private UserRepository repo;

    @BeforeAll
    void setup () { 
        String query = "insert into users(id, email, first_name, last_name) values(:id, :email, :firstName, :lastName)";

        Flux.fromIterable(List.of(
            User.builder().id(UUID.randomUUID()).email("john.doe@gmail.com").firstName("John").lastName("Doe").build(),
            User.builder().id(UUID.randomUUID()).email("jacob.carrey@gmail.com").firstName("Jacob").lastName("Carrey").build(),
            User.builder().id(UUID.randomUUID()).email("joan.beats@gmail.com").firstName("Joan").lastName("Beats").build()
        )).doOnNext(user->{
            client.sql(query)
                .bind("id", user.getId())
                .bind("firstName", user.getFirstName())
                .bind("lastName", user.getLastName())
                .bind("email", user.getEmail()).fetch().rowsUpdated()
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
        });
    }

    @AfterAll
    void tearDown() { 
        client.sql("truncate table users")
            .then()
        .as(StepVerifier::create)
        .verifyComplete();
    }

    @Test
    void testFindByEmail() { 
        client.sql("select * from users")
            .fetch().all().doOnNext(System.out::println).blockLast();
        String email = "jacob.carrey@gmail.com";
        
        StepVerifier.create(repo.findByEmail(email))
            .expectNextMatches(Predicate.not(Objects::isNull))
            .expectComplete()
        .verify();
    }

    @Test
    void testFindByEmailNegative() { 
        String nonExistingEmail = "jacob.scarrey@gmail.com";
        
        StepVerifier.create(repo.findByEmail(nonExistingEmail))
            .expectNextMatches(Objects::isNull)
            .verifyComplete();
    }

}
