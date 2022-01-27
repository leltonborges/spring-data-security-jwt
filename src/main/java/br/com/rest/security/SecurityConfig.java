package br.com.rest.security;

import br.com.rest.services.UserSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserSSService userSSService;

    @Autowired
    private JWTTokenAuthenticationService jwtTokenAuthenticationService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
//        auth.userDetailsService(userSSService)
//                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable()
//                .and()
                .authorizeRequests().antMatchers("/", "/index").permitAll()
                                    .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/index")
                .logoutUrl("/logout");

        http.addFilterBefore(new JWTLoginFilter("/login", authenticationManager(),
                jwtTokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtApiAuthenicationFilter(jwtTokenAuthenticationService),
                UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userSSService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
