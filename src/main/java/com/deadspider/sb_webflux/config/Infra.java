package com.deadspider.sb_webflux.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

@Configuration
//@Profile({"dev", "test"})
@Profile({"!prod"}) // not prod
public class Infra {

    private final String WEB_PORT="8082";
    private Server h2Server;

    @EventListener(ApplicationStartedEvent.class)
    public void start() throws SQLException { 
        this.h2Server = Server.createWebServer("-webPort", WEB_PORT ).start();
        
        System.out.println("started h2 server: " + this.h2Server);
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        this.h2Server.stop();
        System.out.println("stopped h2 server: " + this.h2Server);
    }

}
