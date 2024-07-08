package gift.member;

import gift.exception.FailedLoginException;
import gift.token.JwtProvider;
import org.springframework.stereotype.Component;

@Component
public class MemberService {

    public final MemberRepository memberRepository;
    public final JwtProvider jwtProvider;

    public MemberService(
        MemberRepository memberRepository,
        JwtProvider jwtProvider
    ) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    public String register(Member member) {
        if (memberRepository.existMemberByEmail(member.email())) {
            throw new IllegalArgumentException("Member already exist");
        }
        memberRepository.addMember(member);
        return jwtProvider.generateToken(member);
    }

    public String login(Member member) {
        authenticateMember(member);
        return jwtProvider.generateToken(member);
    }

    public void authenticateMember(Member member) {
        if (!memberRepository.existMemberByEmail(member.email())) {
            throw new FailedLoginException("Member does not exist");
        }
        if (!member.isSamePassword(memberRepository.findMemberByEmail(member.email()))) {
            throw new FailedLoginException("Wrong password");
        }
    }
}
