package gift.service;

import gift.entity.Member;
import gift.exception.EmailDuplicateException;
import gift.exception.MemberNotFoundException;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long registerMember(String email, String password) {
        memberRepository.findByEmail(email)
                .ifPresent((duplicateMember) -> {
                    throw new EmailDuplicateException("Email already in use");
                });
        return memberRepository.save(new Member(email, password)).getId();
    }


    public Long login(String email, String password) {
        Member registeredMember = memberRepository.findMemberByEmailAndPassword(email, password)
                .orElseThrow(() -> new MemberNotFoundException("Email or Password wrong"));
        return registeredMember.getId();
    }

}
