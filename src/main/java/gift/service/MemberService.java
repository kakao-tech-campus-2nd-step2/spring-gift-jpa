package gift.service;

import gift.entity.MemberEntity;
import gift.model.Member;
import gift.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberEntity authenticateToken(Member member) {
        MemberEntity foundMember = memberRepository.findByEmail(member.getEmail());

        if (foundMember == null || !member.getPassword().equals(foundMember.getPassword())) {
            return null;
        }

        return foundMember;
    }

    public MemberServiceStatus save(Member member) {
        if (existsByEmail(member.getEmail())) {
            return MemberServiceStatus.EMAIL_ALREADY_EXISTS;
        }

        // DTO to Entity
        MemberEntity memberEntity = new MemberEntity(member.getEmail(), member.getPassword());
        memberRepository.save(memberEntity);
        return MemberServiceStatus.SUCCESS;
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
