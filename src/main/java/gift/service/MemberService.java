package gift.service;

import gift.dto.request.LoginInfoRequest;
import gift.dto.request.MemberRequest;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long registerMember(MemberRequest member) {
        return memberRepository.registerMember(member.getEmail(), member.getPassword());
    }


    public Long loginMember(LoginInfoRequest loginInfo) {
        return memberRepository.getMemberIdByEmailAndPassword(loginInfo);
    }

    public boolean hasDuplicatedEmail(String email) {
        return memberRepository.hasDuplicatedEmail(email);
    }

}
