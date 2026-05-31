package com.twin.baribari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BaribariApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaribariApplication.class, args);
    }

}
