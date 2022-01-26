package br.com.rest.security;

import br.com.rest.entities.UserSS;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    private JWTTokenAuthenticationService jwtTokenAuthenticationService;
    private AuthenticationManager authenticationManager;

    protected JWTLoginFilter(String url, AuthenticationManager authenticationManager,
                             JWTTokenAuthenticationService jwtTokenAuthenticationService) {
        super(url, authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtTokenAuthenticationService = jwtTokenAuthenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        UserSS userSS = new ObjectMapper().readValue(request.getInputStream(), UserSS.class);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userSS.getUsername(),
                        userSS.getPassword(), userSS.getAuthorities());
//        jwtTokenAuthenticationService.getAuthentication(request); // testa depois
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        jwtTokenAuthenticationService.addAuthorization(response, authResult.getName());
//        chain.doFilter(request, response);

    }
}
