package gift.global.utils;

import gift.api.member.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import javax.crypto.SecretKey;

public class JwtUtil {

    private static final String AUTHORIZATION_TYPE = "Bearer";
    private static final String SECRET_KEY = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private static final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    private JwtUtil() {
        throw new UnsupportedOperationException();
    }

    public static String getTokenFromRequest(HttpServletRequest request) throws NullPointerException {
        return request.getHeader("Authorization").split(" ")[1];
    }

    public static String generateHeaderValue(String token) {
        return AUTHORIZATION_TYPE + " " + token;
    }

    public static String generateAccessToken(Long id, String email, Role role) {
        return Jwts.builder()
            .subject(id.toString())
            .claim("email", email)
            .claim("role", role)
            .signWith(secretKey)
            .compact();
    }

    public static Long getIdFromToken(String token) {
        return Long.valueOf(Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject());
    }
}
