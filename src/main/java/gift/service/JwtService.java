package gift.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;

@Service
public class JwtService {
    private SecretKey key = Jwts.SIG.HS256.key().build();
    public String createJWT(String id){
        return Jwts.builder()
                .claim("id",id)
                .signWith(key)
                .compact();
    }

    public String getJWT() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");

        System.out.println("token = " + token);

        return token.replace("Bearer ","");
    }

    public String getMemberId(){
        String accessToken = getJWT();
        System.out.println(accessToken);
        if(accessToken == null){
            return null;
        }
        if(accessToken.isEmpty()){
            return null;
        }
        Jws<Claims> jws;

        try {
            jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(accessToken);
        } catch (JwtException e) {
            throw new RuntimeException(e);
        }

        return jws.getPayload()
                .get("id", String.class);
    }
}
