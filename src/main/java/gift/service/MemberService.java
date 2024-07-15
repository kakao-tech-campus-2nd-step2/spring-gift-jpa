package gift.service;


import gift.dto.MemberDto;
import gift.entity.Member;
import gift.repository.MemberRepository;

import gift.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String registerMember(MemberDto memberDto) {
        Member newMember = new Member(memberDto.getEmail(), memberDto.getPassword());
        memberRepository.save(newMember);
        return jwtTokenProvider.createToken(memberDto.getEmail());
    }

    public String login(String email, String password) {

        Member member = memberRepository.findByEmail(email);

        // 비밀번호 검증
        if (member != null && password.equals(member.getPassword())) {
            return jwtTokenProvider.createToken(email);
        }
        throw new RuntimeException("Invalid email or password");
    }

    public Member findByEmail(String email) {

        return memberRepository.findByEmail(email);

    }
}
