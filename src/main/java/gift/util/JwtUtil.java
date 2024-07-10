package gift.util;

import gift.model.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

    public String generateToken(Long memberId, String email) {
        return Jwts.builder()
            .subject(memberId.toString())
            .claim("email", email)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 2400000L))
            .signWith(key)
            .compact();
    }

    public Claims extractClaims(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getPayload();
        return claims;
    }

    public boolean isTokenValid(String token, Member member) {
        Claims claims = extractClaims(token);
        return claims.getSubject().equals(member.getId().toString()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration().before(new Date());
    }

}