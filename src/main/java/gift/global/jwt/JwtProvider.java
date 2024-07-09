package gift.global.jwt;

import gift.domain.user.User;
import gift.domain.user.dto.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.constraints.Email;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private static final String SECRET_KEY = "katecamtesttesttesttesttesttesttesttesttesttesttest";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 day

    /**
     * JWT 토큰 생성
     */
    public static String generateToken(User user) {
        return Jwts.builder()
            .claim("email", user.getEmail())
            .claim("id", user.getId())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    public Claims getClaimsBody(String token) {
        Claims claimsBody = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(removeBearer(token))
            .getBody();

        return claimsBody;
    }

    /**
     *  현재 로그인한 사용자 정보 추출
     */
    public UserInfo getUserInfo(String token) {
        Claims claimsBody = getClaimsBody(token);

        UserInfo currentUser = new UserInfo(
            (claimsBody.get("id") instanceof Integer) ? Long.valueOf((Integer) claimsBody.get("id"))
                : (Long) claimsBody.get("id"), claimsBody.get("email").toString());

        return currentUser;
    }

    /**
     * 현재 로그인한 사용자 ID 추출
     */
    public Long getId(String token) {
        Claims claimsBody = getClaimsBody(token);

        Long userId = (claimsBody.get("id") instanceof Integer) ? Long.valueOf((Integer) claimsBody.get("id"))
                : (Long) claimsBody.get("id");

        return userId;
    }

    /**
     * 현재 로그인한 사용자 Email 추출
     */
    public String getEmail(String token) {
        Claims claimsBody = getClaimsBody(token);

        return claimsBody.get("email").toString();
    }

    private String removeBearer(String token) {
        return token.replace("Bearer", "").trim();
    }

    // TODO 유효성 검사 메서드 작성 필요
    public void validateToken(String token) {
        if (token == null || !token.startsWith("Bearer")) {
         // 토큰이 없거나 유효하지 않은 형식입니다.
        }

        var tokenValue = token.substring(7);
        if (tokenValue.isEmpty()) {
         // 토큰이 존재하지 않습니다.
        }
    }
}
