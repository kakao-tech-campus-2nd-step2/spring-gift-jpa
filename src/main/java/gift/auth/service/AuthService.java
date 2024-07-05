package gift.auth.service;

import gift.auth.domain.AuthInfo;
import gift.auth.dto.LoginRequestDto;
import gift.global.token.TokenManager;
import gift.member.domain.Member;
import gift.member.exception.MemberNotFoundException;
import gift.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final TokenManager tokenManager;

    public AuthService(MemberRepository memberRepository, TokenManager tokenManager) {
        this.memberRepository = memberRepository;
        this.tokenManager = tokenManager;
    }

    public String login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByEmailValueAndPasswordValue(loginRequestDto.email(), loginRequestDto.password())
                .orElseThrow(MemberNotFoundException::new);
        return tokenManager.createAccessToken(new AuthInfo(member));
    }
}
