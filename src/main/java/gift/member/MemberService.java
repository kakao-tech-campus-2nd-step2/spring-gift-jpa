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
            throw new IllegalArgumentException(MEMBER_ALREADY_EXISTS);
        }

        memberRepository.save(Member.fromMemberDTO(memberDTO));

        return jwtProvider.generateToken(MemberTokenDTO.fromMemberDTO(memberDTO));
    }

    public String login(MemberDTO memberDTO) {
        Optional<Member> findMember = memberRepository.findById(memberDTO.getEmail());

        if (findMember.isEmpty()) {
            throw new FailedLoginException(MEMBER_NOT_FOUND);
        }

        if (!findMember.get().isSamePassword(findMember.get())) {
            throw new FailedLoginException(WRONG_PASSWORD);
        }

        return jwtProvider.generateToken(MemberTokenDTO.fromMemberDTO(memberDTO));
    }
}
