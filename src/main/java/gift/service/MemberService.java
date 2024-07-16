package gift.service;

import gift.authentication.token.JwtProvider;
import gift.authentication.token.Token;
import gift.domain.Member;
import gift.domain.vo.Email;
import gift.repository.MemberRepository;
import gift.web.dto.request.LoginRequest;
import gift.web.dto.request.member.CreateMemberRequest;
import gift.web.dto.response.LoginResponse;
import gift.web.dto.response.member.CreateMemberResponse;
import gift.web.dto.response.member.ReadMemberResponse;
import gift.web.validation.exception.client.IncorrectEmailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository,
        JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public CreateMemberResponse createMember(CreateMemberRequest request) {
        Member member = request.toEntity();
        return CreateMemberResponse.fromEntity(memberRepository.save(member));
    }

    public ReadMemberResponse readMember(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + id));

        return ReadMemberResponse.fromEntity(member);
    }

    public LoginResponse login(LoginRequest request) {
        Email email = Email.from(request.getEmail());
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IncorrectEmailException(email.getValue()));

        member.matchPassword(request.getPassword());

        Token token = jwtProvider.generateToken(member);

        return new LoginResponse(token);
    }
}
