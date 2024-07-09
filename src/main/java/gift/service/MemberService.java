package gift.service;

import gift.exception.NotFoundElementException;
import gift.exception.UnauthorizedAccessException;
import gift.model.Member;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void existsById(Long memberId) {
        if (memberRepository.existsById(memberId)) return;
        throw new UnauthorizedAccessException("인가되지 않은 요청입니다.");
    }

    public Member findMemberWithId(Long memberId) {
        var member = memberRepository.findById(memberId);
        if (member.isEmpty()) throw new NotFoundElementException("존재하지 않는 리소스에 대한 접근입니다.");
        return member.get();
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
