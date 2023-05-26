package com.why_not_cote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfApplication.class, args);
    }

}
