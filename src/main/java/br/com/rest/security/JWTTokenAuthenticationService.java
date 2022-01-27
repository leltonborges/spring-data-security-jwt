package br.com.rest.security;

import br.com.rest.security.exceptions.JWTErrorExceptions;
import br.com.rest.services.UserSSService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@Configuration
public class JWTTokenAuthenticationService {

    @Autowired
    private UserSSService userSSService;

    @Value("${security.jwt.expiration}")
    private long EXPIRATION_TIME;
//    private final static long EXPIRATION_TIME = 86400000;

//    @Value("${security.jwt.security_key}")
//    private final String SECURITY_KEY = null;
    private final static byte[] SECURITY_KEY = Base64.getEncoder().encode("SECUR!TY_K3Y".getBytes());

    @Value("${security.jwt.token_prefix}")
    private String TOKEN_PREFIX;
//    private final static String TOKEN_PREFIX = "Bearer ";

    @Value("${security.jwt.header_string}")
    private String HEADER_STRING;
//    private final static String HEADER_STRING = "Authorization";

    public void addAuthorization(HttpServletResponse response, String userName) throws IOException {
        String token = generatToken(userName);

        response.addHeader(HEADER_STRING, token);
        response.addHeader("access-control-expose-headers", "Authorization");
        response.addHeader("Content-Type", "application/json");
        response.getWriter().println("""
                {
                    "Authorization": "%s"
                }
                """.formatted(token));
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(HEADER_STRING);

        liberacaoCors(response);

        if (token != null) {
            Claims claims = getClaims(token.replaceFirst(TOKEN_PREFIX, "").trim());
            String userName = claims.getSubject();
            UserDetails userDetails = userSSService.loadUserByUsername(userName);

            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                    userDetails.getPassword(), userDetails.getAuthorities());
        } else {
            return null;
        }
    }


    public String getUserName(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    public Date getDateToke(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getExpiration();
        }
        return null;
    }

    public boolean validateToken(String token) {
        String userName = getUserName(token);
        Date dateExpiration = getDateToke(token);
        if (dateExpiration != null && userName != null
                && dateExpiration.before(new Date(System.currentTimeMillis()))) {
            return true;
        }

        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(SECURITY_KEY)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | IllegalArgumentException | SignatureException ex) {
            ex.printStackTrace();
            throw new JWTErrorExceptions(ex.getMessage());
        }
    }

    private String generatToken(String userName) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    private void liberacaoCors(HttpServletResponse response) {
        if(response.getHeader("Access-Control-Allow-Origin") == null)
            response.addHeader("Access-Control-Allow-Origin", "*");

        if(response.getHeader("Access-Control-Allow-Headers") == null)
            response.addHeader("Access-Control-Allow-Headers", "*");

        if(response.getHeader("Access-Control-Request-Headers") == null)
            response.addHeader("Access-Control-Request-Headers", "*");

        if(response.getHeader("Access-Control-Allow-Methods") == null)
            response.addHeader("Access-Control-Allow-Methods", "*");


    }
}
