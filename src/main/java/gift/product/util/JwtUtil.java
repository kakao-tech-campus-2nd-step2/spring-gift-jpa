package gift.product.util;

import gift.product.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

    // 토큰 생성
    public String generateToken(String email) {
        System.out.println("[JwtUtil] generateToken()");
        long expirationTimeMillis = 3600000;
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expirationTimeMillis);

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .compact();
    }

    // 토큰의 서명 및 유효성 검증
    public boolean isValidToken(String token) {
        System.out.println("[JwtUtil] isValidToken()");
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT token is invalid: " + e.getMessage());
        }
        return false;
    }

    // 토큰에서 클레임 추출
    public Claims extractClaims(String token) {
        System.out.println("[JwtUtil] extractClaims()");
        return Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // HTTP 헤더 인증정보 확인하여 올바른 형식이면 토큰 반환
    public String checkAuthorization(String authorizationHeader) {
        System.out.println("[JwtUtil] checkAuthorization()");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new UnauthorizedException("인증에 필요한 정보가 HTTP 헤더에 존재하지 않습니다.");

        String token = authorizationHeader.substring(7);
        if (!isValidToken(token))
            throw new UnauthorizedException("인증 토큰에 대한 정보가 존재하지 않습니다.");

        return token;
    }

    // 토큰을 이용해 이메일 추출
    public String getEmailByToken(String token) {
        System.out.println("[JwtUtil] getEmailByToken()");
        return extractClaims(token).getSubject();
    }
}
