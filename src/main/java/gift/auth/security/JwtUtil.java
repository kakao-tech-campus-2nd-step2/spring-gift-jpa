package gift.auth.security;

import gift.error.CustomException;
import gift.error.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.jwtExpirationInMs}")
    private Long jwtExpirationInMs;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Long memberId) {
        return Jwts.builder()
                .subject(memberId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(getSigningKey())
                .compact();
    }

    public Long extractId(String token) {
        try {
            return Long.valueOf(Jwts
                    .parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject());
        } catch (ExpiredJwtException exception) {
            throw new CustomException(ErrorCode.AUTHENTICATION_EXPIRED);
        } catch (JwtException exception) {
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        }

    }

}
