package gift.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private final Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());

    // userId와 email을 주체로 하는 토큰 생성
    public String generateToken(String email, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("userId", userId);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
            .signWith(key)
            .compact();
    }

    // 토큰에서 userId 추출
    public Long extractUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

            return claims.get("userId", Long.class);
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    // 토큰에서 email 추출
    public String extractEmail(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

            return claims.get("email", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public boolean validateToken(String token, String email) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

            String tokenEmail = claims.getBody().get("email", String.class);
            Date expiration = claims.getBody().getExpiration();

            return email.equals(tokenEmail) && !isTokenExpired(expiration);
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 토큰 검증 실패
        }
    }

    // 토큰 만료 여부 확인
    private boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
