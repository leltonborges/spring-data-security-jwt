package br.com.rest.services;

import br.com.rest.dtos.UserCreateDTO;
import br.com.rest.dtos.UserDTO;
import br.com.rest.entities.User;
import br.com.rest.repositories.UserRepository;
import br.com.rest.services.excepitons.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements Serializable{
    private static final long serialVersionUID = 8219290633698032358L;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public <S extends User> S save(S entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userRepository.save(entity);
    }

    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Not Found id: "+ id));
    }

    public void delete(User entity) {
        userRepository.delete(entity);
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
