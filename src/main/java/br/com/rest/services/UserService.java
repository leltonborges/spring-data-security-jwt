package br.com.rest.services;

import br.com.rest.entities.User;
import br.com.rest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements Serializable {
    private static final long serialVersionUID = 8219290633698032358L;
    @Autowired
    private UserRepository userRepository;

    public User findByUserName(String userName) {
        return userRepository
                .findByUserName(userName)
                .orElse(new User());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAll(Sort sort) {
        return userRepository.findAll(sort);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }

    public User findById(Long aLong) {
        return userRepository
                .findById(aLong)
                .orElse(new User());
    }

    public <S extends User> Optional<S> findOne(Example<S> example) {
        return userRepository.findOne(example);
    }
}
