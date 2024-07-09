package gift.global.util;

import gift.domain.entity.User;
import gift.domain.exception.TokenExpiredException;
import gift.domain.exception.TokenNotFoundException;
import gift.domain.exception.TokenStringInvalidException;
import gift.domain.exception.TokenUnexpectedErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * User를 기반으로 token을 생성 및 검증하는 유틸 클래스
 */
@Component
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.email())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) //1시간
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            // 토큰이 만료됨
            throw new TokenExpiredException();
        } catch (JwtException e) {
            // 알수 없는 Jwt 예외 발생
            throw new TokenUnexpectedErrorException();
        }
    }

    public void checkPrefixOrThrow(String prefix, String authorizationHeader) {
        if (prefix == null || authorizationHeader == null) {
            throw new TokenNotFoundException();
        }
        if (!authorizationHeader.startsWith(prefix + " ")) {
            throw new TokenStringInvalidException();
        }
    }

    public String extractTokenFrom(String authorizationHeader) {
        try {
            return authorizationHeader.split(" ")[1];
        } catch (NullPointerException e) {
            throw new TokenNotFoundException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new TokenStringInvalidException();
        }
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
}
