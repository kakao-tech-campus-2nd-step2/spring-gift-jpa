package gift.service.auth;

import gift.dto.AuthResponse;
import gift.dto.LoginRequest;
import gift.dto.RegisterRequest;
import gift.exception.DuplicatedEmailException;
import gift.exception.InvalidLoginInfoException;
import gift.exception.NotFoundElementException;
import gift.model.Member;
import gift.model.MemberRole;
import gift.model.Product;
import gift.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    @Value("${SECRET_KEY}")
    private String secretKey;
    @Value("${EXPIRED_TIME}")
    private long expiredTime;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        emailValidation(registerRequest.email());
        var member = createMemberWithMemberRequest(registerRequest);
        var savedMember = memberRepository.save(member);
        var token = createAccessTokenWithMember(savedMember);
        return AuthResponse.of(token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        var member = findMemberByEmail(loginRequest.email());
        loginInfoValidation(member, loginRequest.password());
        var token = createAccessTokenWithMember(member);
        return AuthResponse.of(token);
    }

    private Member findMemberByEmail(String email) {
        var member = memberRepository.findByEmail(email);
        if (member.isEmpty()) throw new InvalidLoginInfoException("로그인 정보가 유효하지 않습니다.");
        return member.get();
    }

    private String createAccessTokenWithMember(Member member) {
        var token = Jwts.builder()
                .subject(member.getId().toString())
                .claim("name", member.getName())
                .claim("role", member.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
        return token;
    }

    private void emailValidation(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException("이미 존재하는 이메일입니다.");
        }
    }

    private void loginInfoValidation(Member member, String password) {
        if (!member.getPassword().equals(password)) {
            throw new InvalidLoginInfoException("로그인 정보가 유효하지 않습니다.");
        }
    }

    private Member createMemberWithMemberRequest(RegisterRequest registerRequest) {
        return new Member(registerRequest.name(), registerRequest.email(), registerRequest.password(), MemberRole.valueOf(registerRequest.role()));
    }
}
