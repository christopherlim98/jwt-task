package auth.security;

import auth.user.User;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import static auth.security.SecurityConstants.AUTHORITIES_KEY;
import static auth.security.SecurityConstants.EXPIRATION_TIME;
import static auth.security.SecurityConstants.AUDIENCE;
import static auth.security.SecurityConstants.SECRET;
import static auth.security.SecurityConstants.ISSUER;
import static auth.security.SecurityConstants.GIVENNAME_KEY;
import static auth.security.SecurityConstants.SURNAME_KEY;
import static auth.security.SecurityConstants.DEPT_KEY;
import static auth.security.SecurityConstants.EMAIL_KEY;

@Service
public class JWTService {
    /**
     * Create JWT from User object
     * @param user logged-in user
     * @return string containing JWT
     */
    public String createJWT(User user){
        final Instant now = Instant.now();
        final String authorities = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        return JWT.create()
            .withIssuer(ISSUER)
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(now.plus(EXPIRATION_TIME, ChronoUnit.MILLIS)))
            .withAudience(AUDIENCE)
            .withSubject(user.getEmail())
            .withClaim(AUTHORITIES_KEY, authorities)
            .withClaim(GIVENNAME_KEY , user.getGivenName())
            .withClaim(SURNAME_KEY, user.getSurname())
            .withClaim(DEPT_KEY , user.getDepartment())
            .withClaim(EMAIL_KEY, user.getEmail())
            .sign(HMAC512(SECRET.getBytes()));
    }

}
