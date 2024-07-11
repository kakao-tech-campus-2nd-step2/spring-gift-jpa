package gift.service;

import gift.model.Member;
import gift.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public boolean login(String email, String password, HttpSession session) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (member.getPassword().equals(password)) {
            session.setAttribute("member", member);
            return true;
        }
        throw new IllegalArgumentException("Invalid email or password");
    }
}
