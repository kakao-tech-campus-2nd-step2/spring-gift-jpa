package gift.controller.auth;

import gift.exception.UnauthorizedAccessException;
import gift.model.MemberRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${SECRET_KEY}")
    private String secretKey;

    public AuthInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var header = getHeader(request);
        var token = getTokenWithAuthorizationHeader(header);
        setMemberIdInAttribute(request, token);
        setMemberRoleInAttribute(request, token);
        return true;
    }

    private Long getMemberIdWithToken(String token) {
        var id = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return Long.parseLong(id);
    }

    private MemberRole getMemberRoleWithToken(String token) {
        var role = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role");
        return MemberRole.valueOf(role.toString());
    }

    private String getTokenWithAuthorizationHeader(String authorizationHeader) {
        var header = authorizationHeader.split(" ");
        if (header.length != 2) throw new IllegalArgumentException("잘못된 헤더 정보입니다.");
        return header[1];
    }

    private String getHeader(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (header == null) throw new UnauthorizedAccessException("인가되지 않은 요청입니다.");
        return header;
    }

    private void setMemberIdInAttribute(HttpServletRequest request, String token) {
        var memberId = getMemberIdWithToken(token);
        request.setAttribute("memberId", memberId);
    }

    private void setMemberRoleInAttribute(HttpServletRequest request, String token) {
        var memberRole = getMemberRoleWithToken(token);
        request.setAttribute("memberRole", memberRole);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
