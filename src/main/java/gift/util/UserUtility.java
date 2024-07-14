package gift.util;

import gift.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserUtility {

    @Value("${spring.var.secret}")
    private String secret;
    @Value("${spring.var.token-prefix}")
    private String tokenPrefix;

    private SecretKey getSecretKey() {
        byte[] bytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    public String makeAccessToken(User user) {
        String accessToken = Jwts.builder()
                .claim("email", user.getEmail())
                .signWith(getSecretKey())
                .compact();
        return accessToken;
    }

    public Object accessTokenToObject(String accessToken) {
        Map<String, Object> obj = new HashMap<>();
        obj.put("accessToken", accessToken);
        return obj;
    }

    public Claims tokenParser(String accessToken) {
        try {
            Jws<Claims> jwt;
            jwt = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(accessToken);
            return jwt.getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}
