package gift.authorization;

import gift.dto.LoginUser;
import gift.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    //private SecretKey secretKey;
    @Value("${jwt.secret}")
    private String secretKey;


    public String generateToken(User user) {
        Date now = new Date();
        Date expiration = new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * 365));
        String accessToken = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("email", user.getEmail())
                .claim("type", user.getType())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
        return accessToken;
    }

    //claims 추출
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // type 얻음
    public String getUserType(String token) {
        try {
            Claims claims = extractClaims(token);
            if (claims.get("type") == null) {
                throw new JwtException("error.invalid.token.type.null");
            }
            return claims.get("type", String.class);
        } catch (JwtException e) {
            throw new JwtException("error.invalid.token");
        }
    }

    // email 얻음
    public String getUserEmail(String token) {
        try {
            Claims claims = extractClaims(token);
            System.out.println(token);
            if (claims.get("email") == null) {
                throw new JwtException("error.invalid.token.type.null");
            }
            return claims.get("email", String.class);
        } catch (JwtException e) {
            System.out.println("email null.. ");
            throw new JwtException("error.invalid.token");
        }
    }

    public boolean checkClaim(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(jwt).getBody();
            return true;
        }catch(ExpiredJwtException e) {   //Token이 만료된 경우 Exception이 발생한다.
            return false;
        }catch(JwtException e) {        //Token이 변조된 경우 Exception이 발생한다.
            return false;
        }
    }

    public ResponseEntity<String> ValidToken(LoginUser loginUser){
        String token = loginUser.getToken();
        if(checkClaim(token)){
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    }

}
