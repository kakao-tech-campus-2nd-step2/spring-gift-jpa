package gift.service;

import gift.model.BearerToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@Component
public class TokenInterceptor {
    static String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

//    @Value("${secret_key}")
//    private String secretKey;

    public Claims getClaims(HttpServletRequest request) throws AuthenticationException {
        String authHeader = request.getHeader("Authorization");
        BearerToken token = new BearerToken(authHeader);
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.getToken())
                .getBody();
        return claims;
    }
}
