package gift.jwtutil;

import gift.dto.TokenDTO;
import gift.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String secretKey;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public TokenDTO makeToken(UserDTO userDTO) {
        String accessToken = Jwts.builder()
                .setSubject(userDTO.email())
                .claim("role", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1800000)) // 30분 만료
                .signWith(getSigningKey())
                .compact();

        return new TokenDTO(accessToken);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public  String getSubject(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody()
                .getSubject();
    }
}
