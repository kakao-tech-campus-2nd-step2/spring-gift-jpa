package gift.service;


import gift.model.Member;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import gift.util.PasswordUtil;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public Member registerMember(Member member) {
        member.setPassword(PasswordUtil.hashPassword(member.getPassword()));
        return memberRepository.save(member);
    }

    public Optional<Member> authenticate(String email, String password) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member. isPresent() && member.get().getPassword().equals(PasswordUtil.hashPassword(password))) {
            return member;
        }
        return Optional.empty();
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

}