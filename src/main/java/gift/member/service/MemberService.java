package gift.member.service;

import gift.member.domain.Token;
import gift.member.domain.Member;
import gift.member.error.ForbiddenException;
import gift.member.repository.MemberRepository;
import gift.member.util.JwtUtil;
import gift.member.util.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public Token register(Member member) {
        member.setPassword(PasswordUtil.encodePassword(member.getPassword()));
        memberRepository.save(member);
        return new Token(jwtUtil.generateToken(member));
    }

    public Token login(Member member) {
        Member existingMember = memberRepository.findByEmail(member.getEmail());
        if (existingMember != null &&
            PasswordUtil.decodePassword(existingMember.getPassword()).equals(member.getPassword())) {
            return new Token(jwtUtil.generateToken(member));
        }
        throw new ForbiddenException("Invalid email or password");
    }

}
