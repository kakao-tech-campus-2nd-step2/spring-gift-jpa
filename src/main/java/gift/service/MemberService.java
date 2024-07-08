package gift.service;

import gift.domain.Member;
import gift.dto.MemberRequest;
import gift.dto.MemberResponse;
import gift.repository.MemberRepository;
import gift.security.SecurityService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecurityService securityService;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder,SecurityService securityService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
}

    public MemberResponse registerMember(MemberRequest requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이메일이 이미 존재합니다.");
        }

        Member member = new Member();
        member.setEmail(requestDto.getEmail());
        member.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Member savedMember = memberRepository.save(member);

        String token = securityService.generateJwtToken(member);
        return new MemberResponse(token);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 " + email + " 가진 회원이 없습니다."));
    }

}
