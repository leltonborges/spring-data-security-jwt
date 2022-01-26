package br.com.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
@RestController
@SpringBootApplication
public class SpringBootSecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
    }

}
