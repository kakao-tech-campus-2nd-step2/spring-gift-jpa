package gift.service;

import gift.exception.UnauthorizedAccessException;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void existsById(Long memberId) {
        if (memberRepository.existsById(memberId)) return;
        throw new UnauthorizedAccessException("인가되지 않은 요청입니다.");
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
