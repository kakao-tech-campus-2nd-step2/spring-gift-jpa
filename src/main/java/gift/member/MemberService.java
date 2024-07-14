package gift.member;

import static gift.exception.ErrorMessage.MEMBER_ALREADY_EXISTS;
import static gift.exception.ErrorMessage.MEMBER_NOT_FOUND;
import static gift.exception.ErrorMessage.WRONG_PASSWORD;

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

    public String register(MemberDTO memberDTO) {
        memberRepository.findById(memberDTO.getEmail())
            .ifPresentOrElse(
                e -> {
                    throw new IllegalArgumentException(MEMBER_ALREADY_EXISTS);
                },
                () -> memberRepository.save(memberDTO.toEntity())
            );

        return jwtProvider.generateToken(memberDTO.toTokenDTO());
    }

    public String login(MemberDTO memberDTO) {
        memberRepository.findById(memberDTO.getEmail())
            .ifPresentOrElse(
                findMember -> verifyPassword(findMember, memberDTO),
                () -> {
                    throw new FailedLoginException(MEMBER_NOT_FOUND);
                }
            );

        return jwtProvider.generateToken(memberDTO.toTokenDTO());
    }

    private void verifyPassword(Member member, MemberDTO memberDTO) {
        if (!member.isSamePassword(memberDTO.toEntity())) {
            throw new IllegalArgumentException(WRONG_PASSWORD);
        }
    }
}
