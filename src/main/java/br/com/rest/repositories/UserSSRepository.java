package br.com.rest.repositories;

import br.com.rest.entities.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserSSRepository extends Repository<User, Long> {
    Optional<User> findByUserName(String userName);
}
