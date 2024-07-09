package gift.service;

import gift.domain.Member;
import gift.domain.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

@Service
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public String signUp(String email, String password) {
        try {
            String token = getToken(email, password);
            var member = new Member(email, password, token);
            memberRepository.save(member);
            return token;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    public String login(String email, String password) {
        try {
            return memberRepository.searchTokenByEmailAndPassword(email, password);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    public boolean isValidToken(String token) {
        try {
            var ftoken = memberRepository.searchTokenByToken(token);
            return Objects.equals(ftoken, token);
        } catch (Exception e) {
            return false;
        }
    }

    private String getToken(String email, String password){
        String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("email", email)
                .claim("password", password)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
        return accessToken;
    }

    private String stringToBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }
}
