package gift.service;

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
        return memberRepository.findById(id)
            .map(LoginMemberDto::from)
            .orElseThrow(() -> new IllegalArgumentException("member 값이 잘못되었습니다."));
    }

    @Transactional
    public void insertMember(MemberRequestDto memberRequestDto) {
        memberRepository.save(memberRequestDto.toEntity());
    }
}
