package br.com.rest.repositories;

import br.com.rest.entities.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<User, Long> {
    Optional<User> findByUserName(String userName);
}
