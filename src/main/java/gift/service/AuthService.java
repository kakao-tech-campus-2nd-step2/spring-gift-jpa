package gift.service;

import gift.controller.auth.Token;
import gift.controller.auth.LoginRequest;
import gift.domain.Member;
import gift.exception.MemberNotExistsException;
import gift.exception.PasswordNotMatchedException;
import gift.login.JwtUtil;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Token login(LoginRequest member) {
        Member m = memberRepository.findByEmail(member.email())
            .orElseThrow(MemberNotExistsException::new);
        if (!member.password().equals(m.getPassword())) {
            throw new PasswordNotMatchedException();
        }
        return new Token(JwtUtil.generateToken(m.getId(), m.getEmail()));
    }
}