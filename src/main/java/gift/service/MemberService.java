package gift.service;

import gift.repository.MemberRepository;
import gift.vo.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final JwtUtil jwtUtil;
    private final MemberRepository repository;

    public MemberService(MemberRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public String login(Member member) {
        String email = member.getEmail();
        Member foundMember = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        foundMember.validateEmail(email); // 이메일 검증
        return createJwtToken(email);
    }

    public String join(Member member) {
        Member joinedMember = repository.save(member);
        return login(joinedMember);
    }

    public String createJwtToken(String email) {
        return jwtUtil.generateToken(email);
    }

}
