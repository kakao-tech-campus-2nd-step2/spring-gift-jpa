package gift.Util;

import gift.entity.User;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;




@Component
public class JWTUtil {

    private final SecretKey key;
    private final int expirationMs;

    public JWTUtil(@Value("${jwt.secretKey}") String jwtSecret,
                       @Value("${jwt.expiredMs}") int jwtExpirationMs) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.expirationMs = jwtExpirationMs;
    }

    public String generateToken(User user) {
        Date date = new Date();
        Date expire = new Date(date.getTime()+expirationMs);
        return Jwts.builder()
                .subject(Integer.toString(user.getId()))
                .issuedAt(date)
                .expiration(expire)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) throws JwtException {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }

    public Integer getUserIdFromToken(String token) {
        return Integer.parseInt(Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }
}
