package com.sparta.spartime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class SpartimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartimeApplication.class, args);
    }
}
