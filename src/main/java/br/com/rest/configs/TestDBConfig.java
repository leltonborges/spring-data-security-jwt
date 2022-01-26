package br.com.rest.configs;

import br.com.rest.entities.User;
import br.com.rest.entities.enuns.Role;
import br.com.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.Set;

@Configuration
@Profile("dev")
public class TestDBConfig implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        User lia = new User(null, "Lia Alves", "lia@gmail.com",
                        passwordEncoder.encode("123"), Set.of(Role.ROLE_CLIENT, Role.ROLE_ADMIN));
        User bob = new User(null, "Bob Alves", "bob@gmail.com",
                passwordEncoder.encode("123"), Set.of(Role.ROLE_CLIENT, Role.ROLE_OPERATOR));

        userService.save(lia);
        userService.save(bob);
    }
}
