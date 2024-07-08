package gift.service;

import gift.domain.Member;
import gift.dto.MemberRequest;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public String register(MemberRequest memberRequest) {

        Member member = memberRepository.save(memberRequest);

        return jwtUtil.generateToken(member);
    }

    public String login(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (member != null && member.getPassword().equals(password)) {
            return jwtUtil.generateToken(member);
        }
        return null;
    }
    public Member getMemberFromToken(String token) {
        String email = jwtUtil.getEmailFromToken(token);

        if(email != null) {
            return memberRepository.findByEmail(email);
        }
        return null;
    }
}
