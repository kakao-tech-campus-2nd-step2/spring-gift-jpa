package gift.member;

import gift.exception.FailedLoginException;
import gift.token.JwtProvider;
import gift.token.MemberTokenDTO;
import java.util.Optional;
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

    public String register(MemberDTO memberDTO) {
        if (memberRepository.existsById(memberDTO.getEmail())) {
            throw new IllegalArgumentException("Member already exist");
        }
        memberRepository.save(new Member(memberDTO));
        return jwtProvider.generateToken(new MemberTokenDTO(memberDTO));
    }

    public String login(MemberDTO memberDTO) {
        authenticateMember(memberDTO);
        return jwtProvider.generateToken(new MemberTokenDTO(memberDTO));
    }

    public void authenticateMember(MemberDTO memberDTO) {
        Optional<Member> findMember = memberRepository.findById(memberDTO.getEmail());
        if (findMember.isEmpty()) {
            throw new FailedLoginException("Member does not exist");
        }
        if (!findMember.get().isSamePassword(memberDTO)) {
            throw new FailedLoginException("Wrong password");
        }
    }
}
