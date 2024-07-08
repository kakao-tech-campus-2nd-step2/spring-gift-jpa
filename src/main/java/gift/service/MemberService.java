package gift.service;

import gift.global.auth.jwt.JwtProvider;
import gift.controller.user.dto.MemberRequest.Login;
import gift.controller.user.dto.MemberRequest.Register;
import gift.model.member.Member;
import gift.model.member.MemberDao;
import gift.validate.InvalidAuthRequestException;
import gift.validate.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final JwtProvider jwtProvider;

    public MemberService(MemberDao memberDao, JwtProvider jwtProvider) {
        this.memberDao = memberDao;
        this.jwtProvider = jwtProvider;
    }

    public void register(Register request) {
        memberDao.findByEmail(request.email()).ifPresent(user -> {
            throw new InvalidAuthRequestException("User already exists.");
        });
        memberDao.insert(request.toEntity());
    }

    public String login(Login request) {
        var user = memberDao.findByEmail(request.email())
            .orElseThrow(() -> new NotFoundException("User not found."));

        if (!user.verifyPassword(request.password())) {
            throw new InvalidAuthRequestException("Password is incorrect.");
        }
        return jwtProvider.createToken(user.getId(), user.getRole());

    }

    public Member getUser(Long memberId) {
        return memberDao.findById(memberId)
            .orElseThrow(() -> new NotFoundException("User not found."));
    }
}
