package gift.global.authentication.jwt;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {
    private final JwtValidator jwtValidator;

    private final long ACCESS_TOKEN_EXPIRE_TIME;  // 1시간
    private final long REFRESH_TOKEN_EXPIRE_TIME;  // 1주

    public JwtGenerator(JwtValidator jwtValidator,
        @Value("${spring.jwt.access_expiration}") long accessExpireTime,
        @Value("${spring.jwt.refresh_expiration}") long refreshExpireTime) {
        this.jwtValidator = jwtValidator;
        this.ACCESS_TOKEN_EXPIRE_TIME = accessExpireTime;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshExpireTime;

    }

    public JwtToken createToken(Long id) {
        long current = System.currentTimeMillis();
        var accessExpireTime = new Date(current + ACCESS_TOKEN_EXPIRE_TIME);
        var refreshExpireTime = new Date(current + REFRESH_TOKEN_EXPIRE_TIME);
        var accessToken = generateToken(id, accessExpireTime, Map.of("type", TokenType.ACCESS));
        var refreshToken = generateToken(id, refreshExpireTime, Map.of("type", TokenType.REFRESH));
        return new JwtToken(accessToken, refreshToken);
    }

    public String reissueAccessToken(String refreshToken) {
        Long id = jwtValidator.validateAndParseToken(refreshToken, TokenType.REFRESH);
        long current = System.currentTimeMillis();
        var accessExpireTime = new Date(current + ACCESS_TOKEN_EXPIRE_TIME);
        return generateToken(id, accessExpireTime, Map.of("type", TokenType.ACCESS));
    }

    private String generateToken(Long id, Date expireTime, Map<String, Object> claims) {
        SecretKey secretKey = jwtValidator.secretKey();

        return Jwts.builder()
            .claims(claims)
            .subject(id.toString())
            .expiration(expireTime)
            .signWith(secretKey)
            .compact();
    }


}
