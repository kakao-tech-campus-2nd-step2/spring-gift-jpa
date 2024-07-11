package gift.service;

import gift.dto.MemberRequestDto;
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

    public void authenticate(String email,String password){
        memberRepository.findByEmailAndPassword(email,password)
            .orElseThrow(() -> new MemberNotFoundException("등록된 유저가 존재하지 않습니다"));
    }
}
