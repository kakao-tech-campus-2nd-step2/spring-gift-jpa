package gift.service;

import gift.domain.Member;
import gift.dto.LoginRequest;
import gift.dto.LoginResponse;
import gift.dto.MemberRequest;
import gift.dto.MemberResponse;
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
            throw new IllegalArgumentException("이메일이 이미 존재합니다.");
        }

        Member member = new Member(requestDto.getEmail(), requestDto.getPassword());
        memberRepository.save(member);

        String token = securityService.generateJwtToken(member);
        return new MemberResponse(token);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = findByEmail(loginRequest.getEmail());
        if (member != null && member.getPassword().equals(loginRequest.getPassword())) {
            String token = jwtTokenProvider.generateToken(member);
            return new LoginResponse(token);
        } else {
            return new LoginResponse("일치하는 이메일이 없거나 비밀번호가 틀렸습니다.");
        }
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 " + email + " 가진 회원이 없습니다."));
    }

    public Member getMemberByToken(String memberToken) {
        //Long memberId = jwtTokenProvider.getUserIdFromToken(memberToken);
        Long memberId = 1L;
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with the given token"));
    }

}
