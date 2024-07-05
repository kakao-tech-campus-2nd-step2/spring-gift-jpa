package gift.Service;

import gift.DTO.JwtToken;
import gift.DTO.LoginDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private String key = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

  public JwtToken createAccessToken(LoginDto loginDto) {
    Instant now = Instant.now();
    Instant expiresAt = now.plus(1, ChronoUnit.DAYS); // 현재 시각에서 1일 뒤로 만료 설정

    String accessToken = Jwts.builder()
      .setSubject(loginDto.getEmail())
      .claim("email", loginDto.getEmail())
      .claim("pw", loginDto.getPassword())
      .setExpiration(Date.from(expiresAt))
      .signWith(Keys.hmacShaKeyFor(key.getBytes()), SignatureAlgorithm.HS256)
      .compact();
    return new JwtToken(accessToken);
  }

  public boolean isValidToken(JwtToken jwtToken) {
    JwtParser jwtParser = Jwts.parser()
      .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
      .build();
    try {
      Jws<Claims> claims = jwtParser.parseClaimsJws(jwtToken.getAccessToken());
      return !claims.getBody().getExpiration().before(new Date());
    } catch (JwtException e) {
      return false;
    }
  }
}
