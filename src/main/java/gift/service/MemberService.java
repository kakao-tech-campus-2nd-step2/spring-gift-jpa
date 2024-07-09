package gift.service;

import gift.dto.request.MemberRequest;
import gift.entity.Member;
import gift.exception.MemberNotFoundException;
import gift.jpaRepo.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long registerMember(MemberRequest memberInfo) {
       return memberRepository.save(new Member(memberInfo.getEmail(), memberInfo.getPassword())).getId();
    }


    public Long loginMember(LoginInfoRequest loginInfo) {
        return memberRepository.getMemberIdByEmailAndPassword(loginInfo);
    }

    public boolean hasDuplicatedEmail(String email) {
        return memberRepository.hasDuplicatedEmail(email);
    }

}
