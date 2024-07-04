package gift.Service;

import gift.DTO.JwtToken;
import gift.DTO.LoginDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private String key="Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

  public JwtToken createAccessToken(LoginDto loginDto){
    String accessToken = Jwts.builder()
      .setSubject(loginDto.getEmail())
      .claim("email", loginDto.getEmail())
      .claim("pw", loginDto.getPw())
      .signWith(Keys.hmacShaKeyFor(key.getBytes()))
      .compact();
    return new JwtToken(accessToken);
  }
}
