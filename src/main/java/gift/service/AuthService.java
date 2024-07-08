package gift.service;

import gift.exception.MemberErrorCode;
import gift.exception.MemberException;
import gift.model.Member;
import gift.model.dto.MemberRequestDto;
import gift.repository.MemberDao;
import gift.util.TokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenProvider tokenProvider;
    private final MemberDao memberDao;

    public AuthService(TokenProvider tokenProvider, MemberDao memberDao) {
        this.tokenProvider = tokenProvider;
        this.memberDao = memberDao;
    }

    public String getToken(MemberRequestDto memberRequestDto) throws MemberException {
        Member member = memberDao.selectMemberByEmail(memberRequestDto.getEmail());
        if (!member.matchPassword(memberRequestDto.getPassword())) {
            throw new MemberException(MemberErrorCode.FAILURE_LOGIN);
        }
        return tokenProvider.generateToken(member);
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
