package gift.member;

import static gift.exception.ErrorMessage.MEMBER_ALREADY_EXISTS;
import static gift.exception.ErrorMessage.MEMBER_NOT_FOUND;
import static gift.exception.ErrorMessage.WRONG_PASSWORD;

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
            throw new IllegalArgumentException(MEMBER_NOT_FOUND);
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
            throw new FailedLoginException(MEMBER_ALREADY_EXISTS);
        }

        if (!findMember.get().isSamePassword(new Member(memberDTO))) {
            throw new FailedLoginException(WRONG_PASSWORD);
        }
    }
}
