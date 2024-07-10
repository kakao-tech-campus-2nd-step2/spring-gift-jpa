package gift.api.member;

import gift.global.exception.ForbiddenMemberException;
import gift.global.exception.UnauthorizedMemberException;
import gift.global.utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long register(MemberRequest memberRequest) {
        if (memberRepository.existsByEmail(memberRequest.email())) {
            throw new EmailAlreadyExistsException();
        }
        return memberRepository.save(new Member(
            memberRequest.email(), memberRequest.password(), memberRequest.role())).getId();
    }

    public void login(MemberRequest memberRequest, String token) {
        if (memberRepository.existsByEmailAndPassword(memberRequest.email(), memberRequest.password())) {
            var id = memberRepository.findByEmail(memberRequest.email()).get().getId();
            if (token.equals(JwtUtil.generateAccessToken(id, memberRequest.email(), memberRequest.role()))) {
                return;
            }
            throw new UnauthorizedMemberException();
        }
        throw new ForbiddenMemberException();
    }
}
