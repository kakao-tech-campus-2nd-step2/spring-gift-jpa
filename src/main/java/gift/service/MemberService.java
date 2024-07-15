package gift.service;

import gift.domain.Member;
import gift.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MemberService {
    private static final long TOKEN_EXPIRATION_TIME_MS = 3600000L; // 1 hour

    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void register(Member member) {
        memberRepository.save(member);
    }

    public String login(String email, String password) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent() && member.get().getPassword().equals(password)) {
            return generateToken(member.get());
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

    private String generateToken(Member member) {
        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim("email", member.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
}
