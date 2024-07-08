package gift.product;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

public class TokenValidationInterceptor implements HandlerInterceptor {

    @Value("${jwt.header}")
    private String AUTHORIZATION;

    @Value("${jwt.type}")
    private String TYPE;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String accessToken = getAccessTokenFromHeader(request);

        if (accessToken.isEmpty()) {
            accessToken = getAccessTokenFromCustomHeader(request);
        }

        if (accessToken.isEmpty()) {
            response.sendError(401, "액세스 토큰이 헤더에 존재하지 않습니다.");
            return false;
        }

        try {
            decodeAccessToken(request, accessToken);
            return true;
        } catch (Exception e) {
            response.sendError(401, "액세스 토큰이 유효하지 않습니다.");
            return false;
        }
    }

    private void decodeAccessToken(HttpServletRequest request, String accessToken) {
        String EncodedSecretKey = Encoders.BASE64.encode(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = Decoders.BASE64.decode(EncodedSecretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        Jws<Claims> claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(accessToken);

        Long memberId = claims.getPayload().get("member_id", Long.class);
        request.setAttribute("memberId", memberId);
    }

    private String getAccessTokenFromCustomHeader(HttpServletRequest request) {
        String accessToken;
        accessToken = (String) (request.getAttribute("CUSTOM_HEADER_AUTHORIZATION"));

        if (accessToken.toLowerCase().startsWith(TYPE.toLowerCase())) {
            accessToken = accessToken.substring(TYPE.length()).trim();
        }
        return accessToken;
    }

    private String getAccessTokenFromHeader(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        String accessToken = "";

        while (headers.hasMoreElements()) {
            String value = headers.nextElement();

            if (value.toLowerCase().startsWith(TYPE.toLowerCase())) {
                accessToken = value.substring(TYPE.length()).trim();
                break;
            }
        }
        return accessToken;
    }
}
