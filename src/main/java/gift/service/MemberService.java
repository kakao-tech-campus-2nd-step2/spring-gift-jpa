package gift.service;

import gift.entity.MemberEntity;
import gift.domain.MemberDTO;
import gift.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberEntity authenticateToken(MemberDTO memberDTO) {
        MemberEntity foundMember = memberRepository.findByEmail(memberDTO.getEmail());

        if (foundMember == null || !memberDTO.getPassword().equals(foundMember.getPassword())) {
            return null;
        }

        return foundMember;
    }

    @Transactional
    public MemberServiceStatus save(MemberDTO memberDTO) {
        if (existsByEmail(memberDTO.getEmail())) {
            return MemberServiceStatus.EMAIL_ALREADY_EXISTS;
        }

        // DTO to Entity
        MemberEntity memberEntity = new MemberEntity(memberDTO.getEmail(), memberDTO.getPassword());
        memberRepository.save(memberEntity);
        return MemberServiceStatus.SUCCESS;
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
