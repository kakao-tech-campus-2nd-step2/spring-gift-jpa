package gift.auth;

import gift.user.User;
import gift.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class JwtUtil {

    @Autowired
    private UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(User user) {
        if(!userService.getUserByEmailAndPassword(user)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        return Jwts.builder()
            .setSubject(user.getEmail())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
            .compact();
    }

    public String extractEmail(String token) {
        Jws<Claims> claims = parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    private Jws<Claims> parseClaimsJws(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token);
    }
}
