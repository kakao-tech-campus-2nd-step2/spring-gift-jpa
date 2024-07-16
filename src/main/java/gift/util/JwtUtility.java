package gift.util;

import gift.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtility {
    private static final Key SECRET_KEY = Jwts.SIG.HS256.key().build();

    public static String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String extractEmail(String authHeader, MemberService memberService) {
        String token = authHeader.substring(7);
        if (memberService.isTokenBlacklisted(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}
