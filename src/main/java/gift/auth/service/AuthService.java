package gift.auth.service;

import gift.auth.dto.LoginReqDto;
import gift.auth.exception.LoginFailedException;
import gift.auth.token.AuthToken;
import gift.auth.token.AuthTokenGenerator;
import gift.member.dto.MemberResDto;
import gift.member.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberService memberService;
    private final AuthTokenGenerator authTokenGenerator;

    public AuthService(MemberService memberService, AuthTokenGenerator authTokenGenerator) {
        this.memberService = memberService;
        this.authTokenGenerator = authTokenGenerator;
    }

    public AuthToken login(String header, LoginReqDto loginReqDto) {

        Long memberId = authTokenGenerator.extractMemberId(header);

        // 회원 정보 조회
        MemberResDto member = memberService.getMember(memberId);
        String password = memberService.getMemberPassword(memberId);

        // 이메일과 비밀번호가 일치하지 않으면 예외 발생
        if (!member.email().equals(loginReqDto.email()) || !password.equals(loginReqDto.password())) {
            throw LoginFailedException.EXCEPTION;
        }

        return authTokenGenerator.generateToken(member);
    }

}
