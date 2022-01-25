package br.com.rest.resources;

import br.com.rest.dtos.UserCreateDTO;
import br.com.rest.dtos.UserDTO;
import br.com.rest.entities.User;
import br.com.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserResource {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> save(@RequestBody UserCreateDTO userCreateDTO){
        userService.save(userService.from(userCreateDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserDTO>> findAll(){

        List<UserDTO> usersDtos = userService.findAll().stream()
                .map(u -> userService.fromUserDTO(u))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(usersDtos);
    }
}
