package gift.service;

import gift.domain.Member;
import gift.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final Key key;

    public TokenService() {
        String secretKey = "s3cr3tK3yF0rJWTt0k3nG3n3r@ti0n12345678"; // 256 bits
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String getBearerTokenFromHeader(String header) {
        if (!header.toLowerCase().startsWith("bearer ")) {
            throw new RuntimeException("Bearer token not included in header");
        }
        return header.substring(7);
    }

    protected String generateToken(Member member) {
        long now = System.currentTimeMillis();
        return Jwts.builder().setSubject(member.getEmail())
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + 3600000)) // 1 hour validity
            .signWith(key).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }
}
