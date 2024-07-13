package gift.member.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

public class JwtProvider {
    @Value("${jwt.key}")
    private String secretKey;

    public String generateToken(final String username, final String password) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", password)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public Map<String, Object> tokenToClaims(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims;
        } catch (Exception e) {
            return null;
        }
    }
}