package gift.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value ("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiredMs}")
    private Long expirationTime;

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getMemberEmailByToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
