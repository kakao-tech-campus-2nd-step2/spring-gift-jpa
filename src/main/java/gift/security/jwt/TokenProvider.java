package gift.security.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {
    private final TokenProperies tokenProperies;

    public TokenProvider(TokenProperies tokenProperies) {
        this.tokenProperies = tokenProperies;
    }

    public String generateToken(String email) {
        Key key = tokenProperies.getKey();

        return Jwts.builder()
                .setSubject(email)
                .setIssuer("example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenProperies.getExpiration()))
                .signWith(key)
                .compact();
    }
}
