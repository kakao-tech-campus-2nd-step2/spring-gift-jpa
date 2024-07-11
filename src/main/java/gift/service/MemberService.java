package gift.service;

import gift.model.Member;
import gift.model.dto.LoginMemberDto;
import gift.model.dto.MemberRequestDto;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public LoginMemberDto selectLoginMemberById(Long id) {
        Member member = memberRepository.findById(id).get();
        return new LoginMemberDto(member.getId(), member.getName(), member.getEmail(),
            member.getRole());
    }

    @Transactional
    public void insertMember(MemberRequestDto memberRequestDto) {
        memberRepository.save(memberRequestDto.toEntity());
    }
}
