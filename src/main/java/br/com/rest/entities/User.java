package br.com.rest.entities;

import br.com.rest.entities.enuns.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 740833986154563190L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String fullName;
    @NotBlank
    @Column(unique = true)
    @Email
    private String userName;
    @NotBlank
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @CollectionTable(name = "user_role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
