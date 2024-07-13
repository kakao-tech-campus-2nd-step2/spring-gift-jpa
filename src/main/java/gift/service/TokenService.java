package gift.service;

import gift.domain.Member;
import gift.domain.TokenAuth;
import gift.exception.UnAuthorizationException;
import gift.repository.token.TokenSpringDataJpaRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


@Service
public class TokenService {

    private final TokenSpringDataJpaRepository tokenRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public TokenService(TokenSpringDataJpaRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String saveToken(Member member){
        String accessToken = Jwts.builder()
                .setSubject(member.getId().toString())
                .claim("email", member.getEmail())
                .signWith(getSecretKey())
                .compact();
        return tokenRepository.save(new TokenAuth(accessToken, member)).getToken();
    }

    public TokenAuth findToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new UnAuthorizationException("인증되지 않은 사용자입니다. 다시 로그인 해주세요."));
    }

    public String getMemberIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    public Claims parseToken(String token) {
        JwtParser parser = (JwtParser) Jwts.parser().setSigningKey(secretKey);
        return parser.parseClaimsJws(token).getBody();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

}

