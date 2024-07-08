package gift.member.service;

import gift.member.domain.TokenDTO;
import gift.member.domain.Member;
import gift.member.error.ForbiddenException;
import gift.member.repository.MemberRepository;
import gift.member.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public TokenDTO register(Member member) {
        memberRepository.save(member);
        return new TokenDTO(jwtUtil.generateToken(member.getEmail()));
    }

    public TokenDTO login(Member member) {
        Member existingMember = memberRepository.findByEmail(member.getEmail());
        if (existingMember != null && existingMember.getPassword().equals(member.getPassword())) {
            return new TokenDTO(jwtUtil.generateToken(member.getEmail()));
        }
        throw new ForbiddenException("Invalid email or password");
    }
}
