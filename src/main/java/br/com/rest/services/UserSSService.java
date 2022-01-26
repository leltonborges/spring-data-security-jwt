package br.com.rest.services;

import br.com.rest.repositories.UserSSRepository;
import br.com.rest.services.excepitons.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSSService implements UserDetailsService {
    @Autowired
    private UserSSRepository userSSRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        br.com.rest.entities.User userEntity = userSSRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("Not Found userName: "+ userName));
        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getAuthorities());
    }
}
