package gift.member.service;

import gift.exception.UnauthorizedException;
import gift.member.model.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Service
public class TokenService {
    private static String secretKey = "";

    public TokenService(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    // JWT 토큰 생성
    public static String generateToken(Member member) {
        return Jwts.builder()
                .setSubject(member.email())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    // 인증되지 않은 토큰을 제공한 경우
    public void validateToken(String token) {
        if (token == null || !isValidToken(token)) {
            throw new UnauthorizedException("인증되지 않은 토큰입니다.");
        }
    }

    // 유효한 토큰인지 검증
    private boolean isValidToken(String token) {
        try {
            String[] parts = token.split("\\."); // 토큰을 헤더, 페이로드, 서명으로 분리
            if (parts.length != 3) {
                return false;
            }

            String header = parts[0];
            String payload = parts[1];
            String signature = parts[2];

            // 헤더와 페이로드를 사용하여 서명 생성
            String signingInput = header + "." + payload;
            Key signingKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

            String expectedSignature = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(signingKey.getAlgorithm().getBytes());

            // 서명 검증
            return signature.equals(expectedSignature);

        } catch (Exception e) {
            return false;
        }
    }
}