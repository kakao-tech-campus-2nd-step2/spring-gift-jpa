package gift.common.auth;

import gift.common.exception.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final String secretKey = "asdsadaaaaaaaaaaaaaaaaaaaaaadfsfdwefafaefweafwsg";
    private final long validityInMilliseconds = 60 * 60 * 1000;  // 1시간

    public String createToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setSubject(email)  // 식별하는데 사용되는 값
            .setIssuedAt(now)   // 토큰 발행 시간
            .setExpiration(validity) // 토큰 만료 시간
            .claim("email", email)  // 클레임 설정
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new InvalidTokenException("지원되지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("토큰이 존재하지 않습니다.");
        } catch (Exception e) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");
        }
    }

    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
            .getSubject();
    }
}
