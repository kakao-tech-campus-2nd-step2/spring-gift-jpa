package gift.product.util;

import gift.product.dao.MemberDao;
import gift.product.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class CertifyUtil {

    private final String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
    private final MemberDao memberDao;

    public CertifyUtil(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public String encodingPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            String subject = claimsJws.getBody().getSubject();
            //if(!memberDao.isExistsMember(Base64.getEncoder().encodeToString(subject.getBytes())))
             //   return false;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT token is invalid: " + e.getMessage());
        }
        return true;
    }

    public Claims extractClaims(String token) {
        System.out.println("[TokenService] extractClaims()");
        return Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public String checkAuthorization(String authorizationHeader) {
        System.out.println("[TokenService] checkAuthorization()");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new UnauthorizedException("인증에 필요한 정보가 HTTP 헤더에 존재하지 않습니다.");

        String token = authorizationHeader.substring(7);
        if (!isValidToken(token))
            throw new UnauthorizedException("인증 토큰에 대한 정보가 존재하지 않습니다.");

        return token;
    }

    public String getEmailByToken(String token) {
        return extractClaims(token).getSubject();
    }
}
