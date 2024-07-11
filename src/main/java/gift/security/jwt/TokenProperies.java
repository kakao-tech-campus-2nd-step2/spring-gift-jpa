package gift.security.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenProperies {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Long getExpiration() {
        return expiration;
    }
}
