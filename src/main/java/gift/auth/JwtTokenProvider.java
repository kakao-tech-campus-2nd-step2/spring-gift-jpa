package gift.auth;

import gift.model.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final Long expirationInSeconds;
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int BEGIN_INDEX = 7;

    public JwtTokenProvider(
        @Value("${jwt.secret}") String secretString,
        @Value("${jwt.expirationSeconds}") Long expirationInSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
        this.expirationInSeconds = expirationInSeconds;
    }

    public String generateToken(Member member) {

        return Jwts.builder()
            .claim("member_id", member.getId())
            .claim("member_role", member.getRole())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expirationInSeconds))
            .signWith(secretKey, SIG.HS512)
            .compact();
    }

    public Claims parseToken(String token) {

        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 토큰 서명"); }
        catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰"); }
        catch (UnsupportedJwtException e) {
            System.out.println("지원하지 않는 JWT 토큰"); }
        catch (IllegalArgumentException e) {
            System.out.println("잘못된 JWT 토큰"); }
        return null;
    }

    public String extractJwtTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(BEGIN_INDEX);
        }

        return null;
    }

}
