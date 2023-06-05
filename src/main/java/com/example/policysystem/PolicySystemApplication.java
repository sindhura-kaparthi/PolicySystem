package com.example.policysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ws.config.annotation.EnableWs;


@SpringBootApplication
@EnableWs
public class PolicySystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(PolicySystemApplication.class, args);
    }

}
