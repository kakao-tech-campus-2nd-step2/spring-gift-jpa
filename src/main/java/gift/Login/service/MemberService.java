package gift.Login.service;

import gift.Login.model.Member;
import gift.Login.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final Key key;

    public MemberService(MemberRepository memberRepository, @Value("${jwt.secret}") String secretKey) {
        this.memberRepository = memberRepository;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Member registerMember(String email, String password) {
        Member member = new Member(email, password);
        memberRepository.save(member);
        return member;
    }

    public String generateToken(Member member) {
        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim("email", member.getEmail())  // 이메일 클레임 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1시간 만료
                .signWith(key)
                .compact();
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public String login(String email, String password) {
        Member member = findMemberByEmail(email);
        if (member != null && member.getPassword().equals(password)) {
            return generateToken(member);
        }
        return null;
    }
}
