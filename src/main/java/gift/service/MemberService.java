package gift.service;

import gift.entity.Member;
import gift.exception.EmailDuplicateException;
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
    public Long registerMember(String email, String password) {
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    throw new EmailDuplicateException(member);
                });
        Member member = new Member(email, password);
        return memberRepository.save(member).getId();
    }


    public Long login(String email, String password) {
        Member registeredMember = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(MemberNotFoundException::new);
        return registeredMember.getId();
    }

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

}
