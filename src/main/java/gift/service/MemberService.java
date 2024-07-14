package gift.service;

import gift.controller.member.dto.MemberRequest;
import gift.controller.member.dto.MemberResponse;
import gift.global.auth.jwt.JwtProvider;
import gift.model.member.Member;
import gift.global.validate.InvalidAuthRequestException;
import gift.global.validate.NotFoundException;
import gift.repository.member.MemberRepository;
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

    //@Transactional
    public void register(MemberRequest.Register request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new InvalidAuthRequestException("User already exists.");
        }
        memberRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public String login(MemberRequest.Login request) {
        Member member = memberRepository.findByEmail(request.email())
            .orElseThrow(() -> new NotFoundException("User not found."));

        if (!member.verifyPassword(request.password())) {
            throw new InvalidAuthRequestException("Password is incorrect.");
        }
        return jwtProvider.createToken(member.getId(), member.getRole());
    }

    @Transactional(readOnly = true)
    public MemberResponse.Info getUser(Long memberId) {
        var member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException("User not found."));
        return MemberResponse.Info.from(member);
    }
}
