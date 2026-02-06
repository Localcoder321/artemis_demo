package dev.localcoder.artemis_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class ArtemisDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArtemisDemoApplication.class, args);
    }
}
