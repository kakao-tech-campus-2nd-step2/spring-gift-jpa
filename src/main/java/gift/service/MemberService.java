package gift.service;


import gift.model.Member;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import gift.util.PasswordUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;


    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public Member registerMember(Member member) {
        member.setPassword(PasswordUtil.hashPassword(member.getPassword()));
        return memberRepository.registerMember(member);
    }

    public Member authenticate(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (member != null && member.getPassword().equals(PasswordUtil.hashPassword(password))) {
            return member;
        }
        return null;
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

}