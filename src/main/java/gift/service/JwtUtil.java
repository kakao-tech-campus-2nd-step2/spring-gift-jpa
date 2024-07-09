package gift.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Token 만료 시간
    private final static long TOKEN_TIME = 60 * 60 *1000L; //60분
    private final static String SECRET_KEY = "mysecretmysecretmysecretmysecretmysecretmysecret";
    private final static SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     *
     * @param email: Email
     * @return JWT 토큰 생성 후 반환
     */
    public String generateToken(Long userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId); // 사용자 ID를 클레임에 추가한다.
        claims.put("email", email); // 사용자 이메일을 클레임에 추가한다.

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(new Date().getTime()))
                .setExpiration(new Date(new Date().getTime() + TOKEN_TIME))
                .signWith(KEY)
                .compact();
    }

    public Long getMemberIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    /**
     * Token에서 Claim 추출
     * @param token JWT 토큰
     * @return Claims
     */
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     *
     * @param authorizationHeader Authorization 헤더
     * @return Bearer 토큰 추출
     */
    public static String getBearerToken(String authorizationHeader) {
        return authorizationHeader.replace("Bearer ", "");
    }

}
