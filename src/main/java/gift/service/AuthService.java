package gift.service;

import gift.exception.MemberErrorCode;
import gift.exception.MemberException;
import gift.model.Member;
import gift.model.dto.MemberRequestDto;
import gift.model.dto.MemberResponseDto;
import gift.repository.MemberRepository;
import gift.util.TokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    public AuthService(TokenProvider tokenProvider, MemberRepository memberRepository) {
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getToken(MemberRequestDto memberRequestDto) throws MemberException {
        Member member = memberRepository.findByEmail(memberRequestDto.getEmail());
        if (!member.matchPassword(memberRequestDto.getPassword())) {
            throw new MemberException(MemberErrorCode.FAILURE_LOGIN);
        }
        return new MemberResponseDto(tokenProvider.generateToken(member));
    }

    public boolean validateAuthorization(String authorizationHeader) {
        if (authorizationHeader == null) {
            return false;
        }
        String type = extractType(authorizationHeader);
        String token = extractToken(authorizationHeader);
        return isBearer(type) && tokenProvider.validateToken(token);
    }

    public Long getMemberId(String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        return Long.parseLong(tokenProvider.getClaims(token).getSubject());
    }

    private boolean isBearer(String type) {
        return type.equals("Bearer");
    }

    private String extractType(String authorizationHeader) {
        return authorizationHeader.split(" ")[0];
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.split(" ")[1];
    }
}
