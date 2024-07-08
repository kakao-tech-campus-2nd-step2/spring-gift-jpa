package gift.auth;

import gift.exception.type.ForbiddenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenService {

    private static final String EMAIL_CLAIM = "email";

    private final SecretKey secretKey;
    private final long accessTokenExpirationMillis;

    public TokenService(TokenProperty tokenProperty) {
        this.secretKey = Keys.hmacShaKeyFor(tokenProperty.getSecretKey().getBytes());
        this.accessTokenExpirationMillis = tokenProperty.getAccessTokenExpirationMillis();
    }

    public String createToken(String email) {
        return Jwts.builder()
                .claim(EMAIL_CLAIM, email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMillis))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractEmail(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get(EMAIL_CLAIM, String.class);
        } catch (Exception e) {
            throw new ForbiddenException("유효하지 않은 토큰입니다.");
        }
    }
}
