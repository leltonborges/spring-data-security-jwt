package br.com.rest.dtos;

import br.com.rest.entities.enuns.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -7062884499859571294L;
    @NotBlank
    private String fullName;
    @NotBlank
    private String userName;
    @NotBlank
    private Set<Role> roles;
}
