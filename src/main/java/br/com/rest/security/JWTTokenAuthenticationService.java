package br.com.rest.security;

import br.com.rest.services.UserSSService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
public class JWTTokenAuthenticationService {

    @Autowired
    private UserSSService userSSService;

    @Value("${security.jwt.expiration}")
    private static long EXPIRATION_TIME;

    @Value("${security.jwt.security_key}")
    private static String SECURITY_KEY;

    @Value("${security.jwt.token_prefix}")
    private static String TOKEN_PREFIX;

    @Value("${security.jwt.header_string}")
    private static String HEADER_STRING;

    public void addAuthorization(HttpServletResponse response, String userName) throws IOException {
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY.getBytes())
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();

        token = TOKEN_PREFIX.concat(" ").concat(token);

        response.addHeader(HEADER_STRING, token);
        response.addHeader("access-control-expose-headers", "Authorization");
        response.getWriter().println("""
                {
                    "Authorization": "%s"
                }
                """.formatted(token));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        Claims claims = getClaims(token);
        if (token != null && claims != null) {
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
            return Jwts.parser()
                    .setSigningKey(SECURITY_KEY)
                    .parseClaimsJws(token.replaceFirst(TOKEN_PREFIX, ""))
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }


}
