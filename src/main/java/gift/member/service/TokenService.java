package gift.member.service;

import gift.exception.UnauthorizedException;
import gift.member.model.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secretKey;
    private byte[] secretKeyBytes;

    public void init() {
        this.secretKeyBytes = secretKey.getBytes();
    }

    // JWT 토큰 생성
    public String generateToken(Member member) {
        return Jwts.builder()
                .setSubject(member.getEmail())
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    // 토큰에서 이메일 추출
    public String extractEmailFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new UnauthorizedException("옳지않은 토큰 포맷입니다,");
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            String email = payload.substring(payload.indexOf(":") + 2, payload.indexOf("\"", payload.indexOf(":") + 2));
            return email;
        } catch (Exception e) {
            throw new UnauthorizedException("옳지않은 토큰 포맷입니다,", e);
        }
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
            Mac mac = Mac.getInstance(SignatureAlgorithm.HS256.getJcaName());
            mac.init(new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName()));
            byte[] expectedSignatureBytes = mac.doFinal(signingInput.getBytes());
            String expectedSignature = Base64.getUrlEncoder().withoutPadding().encodeToString(expectedSignatureBytes);

            // 서명 검증
            return signature.equals(expectedSignature);

        } catch (Exception e) {
            return false;
        }
    }
}