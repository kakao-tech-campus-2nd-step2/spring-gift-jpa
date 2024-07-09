package gift.service;

import gift.domain.Member;
import gift.domain.MemberDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

@Service
public class MemberService {
    @Autowired
    private final MemberDAO memberDAO;

    public MemberService(MemberDAO memberDAO){
        this.memberDAO = memberDAO;
        memberDAO.create();
    }

    public String signUp(Member member) {
        try {
            String token = getToken(member);
            memberDAO.insert(member, token);
            return token;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    public String login(Member member) {
        try {
            return memberDAO.selectTokenbyMember(member);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    public boolean isValidToken(String token) {
        try {
            var ftoken = memberDAO.selectTokenbyToken(token);
            return Objects.equals(ftoken, token);
        } catch (Exception e) {
            return false;
        }
    }

    private String getToken(Member member){
        String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

        String accessToken = Jwts.builder()
                .setSubject(member.email())
                .claim("email", member.email())
                .claim("password", member.password())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
        return accessToken;
    }

    private String stringToBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }
}
