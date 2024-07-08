package gift.product.service;

import gift.product.dto.JwtResponse;
import gift.product.dto.MemberDto;
import gift.product.exception.LoginFailedException;
import gift.product.model.Member;
import gift.product.repository.AuthRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void register(MemberDto memberDto) {
        validateMemberNotExist(memberDto);

        Member member = new Member(memberDto.email(), memberDto.password());
        authRepository.registerMember(member);
    }

    public JwtResponse login(MemberDto memberDto) {
        validateMemberInfo(memberDto);

        Member member = authRepository.findMember(memberDto.email());

        String accessToken = getAccessToken(member);

        return new JwtResponse(accessToken);
    }

    private String getAccessToken(Member member) {
        String EncodedSecretKey = Encoders.BASE64.encode(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = Decoders.BASE64.decode(EncodedSecretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.builder()
            .claim("member_id", member.getMemberId())
            .signWith(key)
            .compact();
    }

    private void validateMemberNotExist(MemberDto memberDto) {
        boolean isMemberExist = authRepository.existsByEmail(memberDto.email());

        if (isMemberExist) {
            throw new IllegalArgumentException("이미 회원으로 등록된 이메일입니다.");
        }
    }

    private void validateMemberInfo(MemberDto memberDto) {
        boolean isMemberExist = authRepository.existsByEmail(memberDto.email());

        if (!isMemberExist) {
            throw new LoginFailedException("회원 정보가 존재하지 않습니다.");
        }

        Member member = authRepository.findMember(memberDto.email());

        if (!memberDto.password().equals(member.getPassword())) {
            throw new LoginFailedException("비밀번호가 일치하지 않습니다.");
        }
    }
}
