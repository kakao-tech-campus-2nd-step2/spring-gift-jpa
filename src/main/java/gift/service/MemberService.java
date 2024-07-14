package gift.service;

import gift.dto.request.MemberRequest;
import gift.domain.Member;
import gift.exception.DuplicateMemberException;
import gift.exception.InvalidCredentialsException;
import gift.exception.MemberNotFoundException;
import gift.repository.member.MemberSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberSpringDataJpaRepository memberRepository;

    @Autowired
    public MemberService(MemberSpringDataJpaRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member register(MemberRequest memberRequest) {
        Optional<Member> oldMember = memberRepository.findByEmail(memberRequest.getEmail());
        if (oldMember.isPresent()) {
            throw new DuplicateMemberException("이미 등록된 이메일입니다.");
        }
        Member member = new Member(memberRequest.getEmail(), memberRequest.getPassword());
        memberRepository.save(member);
        return member;
    }

    public Member authenticate(MemberRequest memberRequest) {
        Member member = memberRepository.findByEmail(memberRequest.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        if (!memberRequest.getPassword().equals(member.getPassword())) {
            throw new InvalidCredentialsException("잘못된 비밀번호입니다.");
        }
        return member;
    }

}
