package gift.config;

import gift.domain.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

@Component
public class JwtProvider {

    private final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    private final Long EXPIRE = 1000L * 60 * 60 * 48;
    public final String PREFIX = "Bearer ";

    public String create(Member member) {
        return Jwts.builder()
                .subject(member.getId().toString())
                .claim("role", member.getRole())
                .expiration(new Date(currentTimeMillis() + EXPIRE))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isVerified(String jwt) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwt);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
