package br.com.rest.services;

import br.com.rest.dtos.UserCreateDTO;
import br.com.rest.dtos.UserDTO;
import br.com.rest.entities.User;
import br.com.rest.repositories.UserRepository;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper mapper;

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

    public User from(UserCreateDTO userCreateDTO){
        return  mapper.map(userCreateDTO, User.class);
    }

    public User from(UserDTO userDTO){
        return mapper.map(userDTO, User.class);
    }

    public UserDTO fromUserDTO(User user){
        return mapper.map(user, UserDTO.class);
    }
}
