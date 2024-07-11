package gift.member;

import gift.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // 비밀번호 암호화 클래스 사용
    }

    // 회원가입 처리
    public Member register(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member(email, encodedPassword);
        return memberRepository.save(member);
    }

    // 컨트롤러 로그인 응답받고 처리를 여기로 옮겨와라
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password);
        if (member == null) {
            throw new UnauthorizedException("로그인 실패: 사용자 정보가 유효하지 않습니다.");
        }
        return member;
    }

    // 토큰 생성 메서드
    public String generateToken(Member member) {
        return TokenService.generateToken(member);
    }
}