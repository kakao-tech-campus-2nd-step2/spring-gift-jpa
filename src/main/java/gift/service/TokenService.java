package gift.service;

import gift.dto.Token;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class TokenService {

    private final static SecretKey key = Jwts.SIG.HS256.key().build();

    public Token generateToken(Long registeredMemberId) {
        String tokenValue = Jwts.builder()
                .claim("memberId", registeredMemberId)
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
