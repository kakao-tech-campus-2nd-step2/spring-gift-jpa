package gift.util;

import gift.domain.member.Member;
import gift.domain.member.MemberResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JwtUtil {
    private final String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    public String generateToken(Member member) {
        return Jwts.builder().subject(String.valueOf(member.id()))
            .claim("name", member.email())
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .compact();
    }

    public MemberResponse getMemberFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token).getPayload();
            return new MemberResponse(Long.parseLong(claims.getSubject()), claims.get("name", String.class));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증 정보입니다");
        }
    }
}
