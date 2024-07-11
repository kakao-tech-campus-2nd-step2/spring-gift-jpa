package gift.member.service;

import gift.exception.UnauthorizedException;
import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder, TokenService tokenService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    // 회원가입 처리
    public Member register(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member();
        member.email(email);
        member.password(encodedPassword);
        return memberRepository.save(member);
    }

    // 컨트롤러 로그인 응답받고 처리를 여기로 옮겨와라
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password);
        if (member == null || !passwordEncoder.matches(password, member.password())) {
            throw new UnauthorizedException("로그인 실패: 사용자 정보가 유효하지 않습니다.");
        }
        return member;
    }

    // 토큰 생성 메서드
    public String generateToken(Member member) {
        return tokenService.generateToken(member);
    }
}
