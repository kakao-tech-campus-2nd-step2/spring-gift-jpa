package gift.service;

import gift.controller.member.dto.MemberResponse.InfoResponse;
import gift.global.auth.jwt.JwtProvider;
import gift.controller.member.dto.MemberRequest.Login;
import gift.controller.member.dto.MemberRequest.Register;
import gift.model.member.Member;
import gift.repository.MemberJpaRepository;
import gift.validate.InvalidAuthRequestException;
import gift.validate.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;
    private final JwtProvider jwtProvider;

    public MemberService(MemberJpaRepository memberJpaRepository, JwtProvider jwtProvider) {
        this.memberJpaRepository = memberJpaRepository;
        this.jwtProvider = jwtProvider;
    }

    public void register(Register request) {
        if (memberJpaRepository.existsByEmail(request.email())) {
            throw new InvalidAuthRequestException("User already exists.");
        }
        memberJpaRepository.save(request.toEntity());
    }

    public String login(Login request) {
        var member = memberJpaRepository.findByEmail(request.email())
            .orElseThrow(() -> new NotFoundException("User not found."));

        if (!member.verifyPassword(request.password())) {
            throw new InvalidAuthRequestException("Password is incorrect.");
        }
        return jwtProvider.createToken(member.getId(), member.getRole());

    }

    public InfoResponse getUser(Long memberId) {
        var member = memberJpaRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException("User not found."));
        return InfoResponse.from(member);
    }
}
