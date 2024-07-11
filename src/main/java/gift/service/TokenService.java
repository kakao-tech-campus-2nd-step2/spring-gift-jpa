package gift.service;

import gift.dto.response.TokenResponse;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    private static final SecretKey KEY = Jwts.SIG.HS256.key().build();
    private static final int JWT_EXPIRATION_IN_MS = 1000 * 60 * 60 * 2;

    public TokenResponse generateToken(Long registeredMemberId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_IN_MS);
        String tokenValue = Jwts.builder()
                .claim("memberId", registeredMemberId)
                .expiration(expiryDate)
                .signWith(KEY)
                .compact();
        return new TokenResponse(tokenValue);
    }

    public Long getMemberIdByToken(String tokenValue) {
        String resultOfString = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(tokenValue)
                .getPayload()
                .get("memberId")
                .toString();
        return Long.parseLong(resultOfString);
    }

    public boolean isValidateToken(String tokenValue) {
        try {
            Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(tokenValue);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
