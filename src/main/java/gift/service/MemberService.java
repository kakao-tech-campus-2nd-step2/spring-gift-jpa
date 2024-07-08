package gift.service;

import gift.model.Member;
import gift.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Member register(String email, String password) {
        if (memberRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encodedPassword);
        return memberRepository.save(member);
    }

    public Member authenticate(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (member != null && passwordEncoder.matches(password, member.getPassword())) {
            return member;
        }
        return null;
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }
}