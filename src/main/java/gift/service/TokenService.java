package gift.service;

import gift.dto.Token;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    private final static SecretKey key = Jwts.SIG.HS256.key().build();
    private final static int jwtExpirationInMs = 7200000; // 2hour

    public Token generateToken(Long registeredMemberId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        String tokenValue = Jwts.builder()
                .claim("memberId", registeredMemberId)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
        return new Token(tokenValue);
    }

    public Long getMemberIdByToken(String tokenValue) {
        String resultOfString = Jwts.parser()
                .verifyWith(key)
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
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(tokenValue);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
