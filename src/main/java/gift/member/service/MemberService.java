package gift.member.service;

import gift.member.domain.Member;
import gift.member.exception.MemberAlreadyExistsException;
import gift.member.exception.MemberNotFoundException;
import gift.member.persistence.MemberRepository;
import gift.member.service.dto.MemberInfoParam;
import gift.member.service.dto.MemberSignInInfo;
import gift.member.service.dto.MemberSignupInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public MemberSignupInfo signUp(MemberInfoParam memberInfoParam) {
        memberRepository.findByUsername(memberInfoParam.username())
                .ifPresent(u -> {
                    throw new MemberAlreadyExistsException();
                });

        Member newMember = new Member(memberInfoParam.username(),
                PasswordProvider.encode(memberInfoParam.username(), memberInfoParam.password()));
        memberRepository.save(newMember);

        String token = jwtProvider.generateToken(newMember.getUsername(), newMember.getPassword());

        return MemberSignupInfo.of(newMember.getId(), token);
    }

    @Transactional(readOnly = true)
    public MemberSignInInfo signIn(MemberInfoParam memberInfoParam) {
        Member savedMember = memberRepository.findByUsername(memberInfoParam.username())
                .orElseThrow(MemberNotFoundException::new);
        if (PasswordProvider.match(memberInfoParam.username(), memberInfoParam.password(),
                savedMember.getPassword())) {
            throw new MemberNotFoundException();
        }

        String token = jwtProvider.generateToken(savedMember.getUsername(), savedMember.getPassword());

        return MemberSignInInfo.of(token);
    }
}
