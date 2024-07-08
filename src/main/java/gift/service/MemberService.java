package gift.service;

import gift.domain.Member;
import gift.dto.MemberRequestDto;
import gift.dto.MemberResponseDto;
import gift.exception.MemberNotFoundException;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void save(MemberRequestDto memberRequestDto){
        memberRepository.save(memberRequestDto.toEntity());
    }

    public boolean findByEmailAndPassword(String email, String password) {
        memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new MemberNotFoundException("해당 정보를 가진 유저가 존재하지 않습니다."));
        return true;
    }

    public MemberResponseDto findByEmail(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 정보를 가진 유저가 존재하지 않습니다."));
        return MemberResponseDto.from(member);
    }
}
