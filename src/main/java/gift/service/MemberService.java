package gift.service;

import gift.dto.MemberRequest;
import gift.entity.Member;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(MemberRequest memberRequest) {
        String encodedPassword = passwordEncoder.encode(memberRequest.getPassword());
        Member member = new Member();
        member.setEmail(memberRequest.getEmail());
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        return jwtUtil.generateToken(member.getId(), member.getEmail(), "USER");
    }

    public String authenticate(MemberRequest memberRequest) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberRequest.getEmail());
        if (optionalMember.isPresent() && passwordEncoder.matches(memberRequest.getPassword(), optionalMember.get().getPassword())) {
            Member member = optionalMember.get();
            return jwtUtil.generateToken(member.getId(), member.getEmail(), "USER");
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}
