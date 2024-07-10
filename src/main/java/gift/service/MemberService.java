package gift.service;

import gift.entity.Member;
import gift.exception.EmailDuplicateException;
import gift.exception.MemberNotFoundException;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long registerMember(String email, String password) {
        Optional<Member> existingMember = memberRepository.findByEmail(email);

        if (existingMember.isPresent()) {
            throw new EmailDuplicateException(existingMember.get());
        }

        return memberRepository.save(new Member(email, password)).getId();
    }


    public Long login(String email, String password) {
        Member registeredMember = memberRepository.findMemberByEmailAndPassword(email, password)
                .orElseThrow(MemberNotFoundException::new);
        return registeredMember.getId();
    }

}
