package gift.member.service;

import gift.member.domain.Member;
import gift.member.exception.MemberNotFoundException;
import gift.member.repository.MemberRepository;
import gift.member.dto.MemberServiceDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    public void createMember(MemberServiceDto memberServiceDto) {
        memberRepository.save(memberServiceDto.toMember());
    }

    public void updateMember(MemberServiceDto memberServiceDto) {
        validateMemberExists(memberServiceDto.id());
        memberRepository.save(memberServiceDto.toMember());
    }

    public void deleteMember(Long id) {
        validateMemberExists(id);
        memberRepository.deleteById(id);
    }

    private void validateMemberExists(Long id) {
        memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }
}
