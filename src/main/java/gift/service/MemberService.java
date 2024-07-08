package gift.service;

import gift.model.Member;
import gift.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }


    public Member authenticateToken(Member member) {
        Member foundMember = memberRepository.findByEmail(member.getEmail());

        if (foundMember == null || !member.getPassword().equals(foundMember.getPassword())) {
            return null;
        }

        return foundMember;
    }

    public MemberServiceStatus save(Member member) {
        if (existsByEmail(member.getEmail())) {
            return MemberServiceStatus.EMAIL_ALREADY_EXISTS;
        }

        memberRepository.save(member);
        return MemberServiceStatus.SUCCESS;
    }

    public boolean existsByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member != null;
    }
}
