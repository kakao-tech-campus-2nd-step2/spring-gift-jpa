package gift.service;

import gift.domain.Member;
import gift.domain.TokenAuth;
import gift.exception.UnAuthorizationException;
import gift.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String saveToken(Member member){
        String accessToken = Jwts.builder()
                .setSubject(member.getId().toString())
                .claim("email", member.getEmail())
                .signWith(getSecretKey())
                .compact();
        return tokenRepository.save(accessToken, member.getEmail());
    }

    public TokenAuth findToken(String token){
        return tokenRepository.findTokenByToken(token)
                .orElseThrow(()-> new UnAuthorizationException("인증되지 않은 사용자입니다. 다시 로그인 해주세요."));
    }

    public String getMemberIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }


    public Claims parseToken(String token) {
        SecretKey key = getSecretKey();
        JwtParser parser = (JwtParser) Jwts.parser().setSigningKey(key);
        return parser.parseClaimsJws(token).getBody();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
