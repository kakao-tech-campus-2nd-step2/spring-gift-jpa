package gift.jwt;

import gift.model.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;


@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public String generateToken(Member member) {
        return Jwts.builder()
                .setSubject(member.getEmail())
                .claim("password", member.getPassword())
                .signWith(key)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getPasswordFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("password", String.class);
    }
}