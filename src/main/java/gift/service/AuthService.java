package gift.service;

import gift.common.exception.AuthenticationException;
import gift.controller.dto.request.SignInRequest;
import gift.model.Member;
import gift.repository.MemberRepository;
import gift.security.TokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    public AuthService(TokenProvider tokenProvider, MemberRepository memberRepository) {
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
    }

    public String signIn(SignInRequest request) {
        Member member = findEmailAndPassword(request);
        return tokenProvider.generateToken(member.getId(), member.getEmail(), member.getRole());
    }

    private Member findEmailAndPassword(SignInRequest request) {
        return memberRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new AuthenticationException("Invalid username or password."));
    }
}
