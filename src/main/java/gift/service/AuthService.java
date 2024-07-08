package gift.service;

import gift.common.exception.AuthenticationException;
import gift.controller.dto.request.SignInRequest;
import gift.model.Member;
import gift.repository.MemberDao;
import gift.security.TokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final TokenProvider tokenProvider;
    private final MemberDao memberDao;

    public AuthService(TokenProvider tokenProvider, MemberDao memberDao) {
        this.tokenProvider = tokenProvider;
        this.memberDao = memberDao;
    }

    public String signIn(SignInRequest request) {
        Member member = findEmailAndPassword(request);
        return tokenProvider.generateToken(member.getId(), member.getEmail(), member.getRole());
    }

    private Member findEmailAndPassword(SignInRequest request) {
        return memberDao.findByEmailAndPassword(request)
                .orElseThrow(() -> new AuthenticationException("Invalid username or password."));

    }
}
