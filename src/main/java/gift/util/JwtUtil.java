package gift.util;

import gift.authentication.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value ("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiredMs}")
    private Long expirationTime;

    public String generateToken(Long id,String email) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",id);
        claims.put("email",email);
        return Jwts.builder()
                .setSubject(id.toString())
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public UserDetails getUserDetail(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody();
        Long id = claims.get("id", Long.class);
        String email = claims.get("email", String.class);
        return new UserDetails(id,email);

        //이 부분을 resolver..?
    }
}
