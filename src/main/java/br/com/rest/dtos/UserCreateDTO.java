package br.com.rest.dtos;

import br.com.rest.entities.enuns.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO implements Serializable {
    private static final long serialVersionUID = 4345717468841932527L;

    @NotBlank
    @JsonProperty("full_name")
    private String fullName;
    @NotBlank
    @Email
    @JsonProperty("user_name")
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    private Set<Role> roles;
}
