package gift.service;

import gift.exceptionAdvisor.MemberServiceException;
import gift.model.Member;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import javax.crypto.SecretKey;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTool {

    private final SecretKey key = SIG.HS256.key().build();

    public AuthenticationTool() {
    }

    public String makeToken(Member member) {
        return Jwts.builder().claim("id", member.getId())
            .signWith(key).compact();
    }

    public long parseToken(String token) {
        try {
            var claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
                .getPayload();//TODO : 수정필요
            return Long.parseLong(claims.get("id").toString());
        } catch (JwtException e) {
            throw new MemberServiceException("JWT 인증 실패", HttpStatus.FORBIDDEN);
        }

    }


}
