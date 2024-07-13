package gift.service;

import gift.exception.ErrorCode;
import gift.exception.RepositoryException;
import gift.model.Member;
import gift.model.MemberDTO;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDTO createMember(MemberDTO memberDTO) {
        Member member = new Member(memberDTO.id(), memberDTO.email(), memberDTO.password());
        return convertToDTO(memberRepository.save(member));
    }

    public Member findMemberByCredentials(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password)
            .orElseThrow(() -> new RepositoryException(ErrorCode.MEMBER_NOT_FOUND, email));
    }

    private MemberDTO convertToDTO(Member member) {
        return new MemberDTO(member.getId(), member.getEmail(), member.getPassword());
    }
}
