package gift.service;

import gift.exception.MemberErrorCode;
import gift.exception.MemberException;
import gift.model.Member;
import gift.model.dto.MemberRequestDto;
import gift.repository.MemberDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String SECRET_KEY = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    private final MemberDao memberDao;

    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public String getToken(MemberRequestDto memberRequestDto) throws MemberException {
        Member member = memberDao.selectMemberByEmail(memberRequestDto.getEmail());
        if (!member.matchPassword(memberRequestDto.getPassword())) {
            throw new MemberException(MemberErrorCode.FAILURE_LOGIN);
        }
        return generateToken(member);
    }

    private String generateToken(Member member) {
        return Jwts.builder()
            .claim("name", member.getName())
            .claim("role", member.getRole())
            .subject(member.getId().toString())
            .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
            .compact();
    }

    public boolean validateAuthorization(String authorizationHeader) {
        if (authorizationHeader == null) {
            return false;
        }
        String type = extractType(authorizationHeader);
        String token = extractToken(authorizationHeader);

        return isBearer(type) && validateToken(token);
    }

    private boolean validateToken(String token) {
        try {
            Claims payload = getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims getClaims(String token) throws JwtException {
        Jws<Claims> jws = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
            .build()
            .parseSignedClaims(token);
        return jws.getPayload();
    }

    private boolean isBearer(String type) {
        return type.equals("Bearer");
    }

    private String extractType(String authorizationHeader) {
        return authorizationHeader.split(" ")[0];
    }

    public String extractToken(String authorizationHeader) {
        return authorizationHeader.split(" ")[1];
    }
}
