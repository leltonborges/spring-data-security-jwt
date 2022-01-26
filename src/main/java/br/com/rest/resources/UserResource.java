package br.com.rest.resources;

import br.com.rest.dtos.UserCreateDTO;
import br.com.rest.dtos.UserDTO;
import br.com.rest.entities.User;
import br.com.rest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
//"http://192.168.1.67:8080","http://192.168.1.67:81",
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:81"})
@RestController
@RequestMapping("/users")
public class UserResource {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> saveUser(@RequestBody UserCreateDTO userCreateDTO) {
        userService.save(userService.from(userCreateDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Void> updateUser(@PathVariable("id") Long id, @RequestBody UserCreateDTO userCreateDTO) {
        User userEntity = userService.findById(id);
        User userUpdate = new User();
        mapper.map(userCreateDTO, userUpdate);
        if(userEntity.getId() != null) userUpdate.setId(userEntity.getId());

        userService.save(userUpdate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        System.out.println("id: "+ id);
        userService.delete(userService.findById(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserDTO>> findAll() {

        List<UserDTO> usersDtos = userService.findAll().stream()
                .map(u -> userService.fromUserDTO(u))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(usersDtos);
    }
}
