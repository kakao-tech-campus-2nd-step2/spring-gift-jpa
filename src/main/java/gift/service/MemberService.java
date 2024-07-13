package gift.service;

import gift.DTO.LoginRequest;
import gift.DTO.LoginResponse;
import gift.DTO.SignupRequest;
import gift.DTO.SignupResponse;
import gift.domain.Member;
import gift.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final Key key;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        String secretKey = "s3cr3tK3yF0rJWTt0k3nG3n3r@ti0n12345678"; // 256 bits
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public SignupResponse registerMember(SignupRequest signupRequest) {
        memberRepository.findByEmail(signupRequest.getEmail()).ifPresent(p -> {
            throw new RuntimeException("Email already exists");
        });
        Member member = new Member(signupRequest.getEmail(), signupRequest.getPassword());
        memberRepository.save(member);

        String welcome = "Welcome, " + member.getEmail() + "!";
        return new SignupResponse(welcome);
    }

    public LoginResponse loginMember(LoginRequest loginRequest) throws Exception {
        Optional<Member> user = memberRepository.findByEmail(loginRequest.getEmail()); // Email이 PK
        user.orElseThrow(() -> new RuntimeException("Invalid email or password"));
        Member registeredMember = user.get();
        if (registeredMember.getPassword().equals(loginRequest.getPassword())) {
            String token = generateToken(registeredMember);
            return new LoginResponse(token);
        }
        throw new Exception("Invalid email or password");
    }

    public Optional<Member> getMemberByEmail(String email) {
       return memberRepository.findByEmail(email);
    }

    private String generateToken(Member member) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .setSubject(member.getEmail())
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + 3600000)) // 1 hour validity
            .signWith(key)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
