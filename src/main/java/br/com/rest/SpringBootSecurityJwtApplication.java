package br.com.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EntityScan(basePackages = {"br.com.rest.entities"})
@ComponentScan(basePackages = {"br.com.rest.*"})
@EnableJpaRepositories(basePackages = {"br.com.rest.repositories"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@SpringBootApplication
public class SpringBootSecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
    }

}
