package gift.service;

import static gift.login.JwtUtil.generateToken;

import gift.controller.member.MemberDto;
import gift.controller.auth.Token;
import gift.exception.MemberNotExistsException;
import gift.exception.PasswordNotMatchedException;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Token login(MemberDto member) {
        var m = memberRepository.findByEmail(member.email());
        m.orElseThrow(MemberNotExistsException::new);
        if (!member.password().equals(m.get().password())) {
            throw new PasswordNotMatchedException();
        }
        Token token = new Token(generateToken(member.email(), member.password()));
        return token;
    }
}