package ai.tech.blog.common.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;

@Service
public class JwtProvider {
    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateJwt(final Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(key)
                .compact();
    }

    public boolean validateJwt(String jwt) {
        Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromJwt(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }
}