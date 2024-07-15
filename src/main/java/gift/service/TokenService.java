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

    private final String secretKey;

    public TokenService(TokenSpringDataJpaRepository tokenRepository, @Value("${jwt.secret-key}") String secretKey) {
        this.tokenRepository = tokenRepository;
        this.secretKey = secretKey;
    }

    public String saveToken(Member member){
        String accessToken = Jwts.builder()
                .setSubject(member.getId().toString())
                .claim("email", member.getEmail())
                .signWith(getSecretKey())
                .compact();
        TokenAuth newTokenAuth = new TokenAuth(accessToken, member);
        tokenRepository.save(newTokenAuth);
        return newTokenAuth.getToken();
    }

    public TokenAuth findToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new UnAuthorizationException("인증되지 않은 사용자입니다. 다시 로그인 해주세요."));
    }

//    public String getMemberIdFromToken(String token) {
//        Claims claims = parseToken(token);
//        return claims.getSubject();
//    }
//
//    public Claims parseToken(String token) {
//        JwtParser parser = Jwts.parserBuilder()
//                .setSigningKey(getSecretKey())
//                .build();
//        return parser.parseClaimsJws(token).getBody();
//    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

}

