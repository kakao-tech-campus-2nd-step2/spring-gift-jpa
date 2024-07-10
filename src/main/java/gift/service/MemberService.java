package gift.service;

import gift.constants.Messages;
import gift.domain.Member;
import gift.dto.MemberRequestDto;
import gift.dto.MemberResponseDto;
import gift.exception.MemberNotFoundException;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void save(MemberRequestDto memberRequestDto){
        memberRepository.save(memberRequestDto.toEntity());
    }

    @Transactional(readOnly = true)
    public boolean checkMemberExistsByIdAndPassword(String email, String password) {
        memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new MemberNotFoundException(Messages.NOT_FOUND_MEMBER));
        return true;
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findByEmail(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(Messages.NOT_FOUND_MEMBER));
        return MemberResponseDto.from(member);
    }
}
