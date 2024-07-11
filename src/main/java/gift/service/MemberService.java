package gift.service;

import gift.domain.Member;
import gift.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    public MemberService(MemberRepository memberRepository, JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
    }


    public Member register(Member member) throws Exception {
        Member getMember = memberRepository.findByEmail(member.getEmail());

        if (getMember != null) {
            throw new Exception("해당 email의 계정이 이미 존재합니다.");
        }

        return memberRepository.save(member);

    }

    public String login(Member member) throws Exception {
        Member getMember = memberRepository.findByEmail(member.getEmail());

        if (getMember == null || !Objects.equals(getMember.getPassword(), member.getPassword())) {
            throw new Exception("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return jwtService.generateToken(member);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

}
