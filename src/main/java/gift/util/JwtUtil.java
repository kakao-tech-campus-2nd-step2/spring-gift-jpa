package gift.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 자동으로 512비트 키 생성
    private static final long EXPIRATION_TIME = 86400000L; // 1일

    public static String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key)
            .compact();
    }

    public static String extractEmail(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);

        Claims claims = claimsJws.getBody();
        return claims.getSubject();
    }

    public static boolean validateToken(String token, String email) {
        String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    private static boolean isTokenExpired(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getExpiration().before(new Date());
    }
}
