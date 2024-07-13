package gift.service;

import gift.domain.Member;
import gift.dto.LoginRequest;
import gift.dto.LoginResponse;
import gift.dto.MemberRequest;
import gift.dto.MemberResponse;
import gift.exception.ErrorMessage;
import gift.repository.MemberRepository;
import gift.security.JwtTokenProvider;
import gift.security.SecurityService;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityService securityService;

    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, SecurityService securityService) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityService = securityService;
}

    public MemberResponse registerMember(MemberRequest requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException(ErrorMessage.MEMBER_EMAIL_ALREADY_EXISTS);
        }

        Member member = new Member(requestDto.getEmail(), requestDto.getPassword());
        memberRepository.save(member);

        String token = securityService.generateJwtToken(member);
        return new MemberResponse(token);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException(ErrorMessage.EMAIL_NOT_FOUND));
        if (member != null && member.getPassword().equals(loginRequest.getPassword())) {
            String token = jwtTokenProvider.generateToken(member);
            return new LoginResponse(token);
        } else {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOGIN_CREDENTIALS);
        }
    }

    public Member getMemberByToken(String memberToken) {
        //Long memberId = jwtTokenProvider.getUserIdFromToken(memberToken);
        Long memberId = 1L;
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with the given token"));
    }

}
