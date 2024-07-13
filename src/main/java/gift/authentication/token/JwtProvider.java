package gift.authentication.token;

import gift.domain.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final SecretKey key;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private final String MEMBER_ID_CLAIM_KEY = "memberId";

    public JwtProvider(@Value("${jwt.secretkey}") String secret) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public Token generateToken(Member member) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = new Date(nowMillis + expirationTime);

        return Token.from(Jwts.builder()
            .claim(MEMBER_ID_CLAIM_KEY, member.getId())
            .issuedAt(now)
            .expiration(expiration)
            .signWith(key)
            .compact());
    }

}
