package gift.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

public class Interceptor {
    static String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

//    @Value("${secret_key}")
//    private String secretKey;

    public String getToken(HttpServletRequest request) throws AuthenticationException {
        // 요청 헤더에서 Authorization 헤더 값을 가져옴
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Authorization 헤더에서 Bearer 토큰을 추출
            String token = authHeader.substring(7);
            System.out.println(token);
            return token;
        }
        else{
            throw new AuthenticationException("헤더 혹은 토큰이 유효하지 않습니다.");
        }
    }

    public Claims getClaims(HttpServletRequest request) throws AuthenticationException{
        String token = getToken(request);
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException e) {
        return e.getMessage();
    }
}
