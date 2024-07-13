package gift.service;

import gift.model.Member;
import gift.repository.MemberRepository;
import gift.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerMember(Member member) {
        // 비밀번호 암호화
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return jwtTokenProvider.createToken(member.getEmail());
    }

    public String login(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        // 비밀번호 검증
        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            return jwtTokenProvider.createToken(email);
        }
        throw new RuntimeException("Invalid email or password");
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
