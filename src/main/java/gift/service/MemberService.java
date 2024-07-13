package gift.service;

import gift.controller.member.dto.MemberRequest;
import gift.controller.member.dto.MemberResponse;
import gift.global.auth.jwt.JwtProvider;
import gift.model.member.Member;
import gift.repository.MemberJpaRepository;
import gift.global.validate.InvalidAuthRequestException;
import gift.global.validate.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;
    private final JwtProvider jwtProvider;

    public MemberService(MemberJpaRepository memberJpaRepository, JwtProvider jwtProvider) {
        this.memberJpaRepository = memberJpaRepository;
        this.jwtProvider = jwtProvider;
    }

    //@Transactional
    public void register(MemberRequest.Register request) {
        if (memberJpaRepository.existsByEmail(request.email())) {
            throw new InvalidAuthRequestException("User already exists.");
        }
        memberJpaRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public String login(MemberRequest.Login request) {
        Member member = memberJpaRepository.findByEmail(request.email())
            .orElseThrow(() -> new NotFoundException("User not found."));

        if (!member.verifyPassword(request.password())) {
            throw new InvalidAuthRequestException("Password is incorrect.");
        }
        return jwtProvider.createToken(member.getId(), member.getRole());
    }

    @Transactional(readOnly = true)
    public MemberResponse.Info getUser(Long memberId) {
        var member = memberJpaRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException("User not found."));
        return MemberResponse.Info.from(member);
    }
}
