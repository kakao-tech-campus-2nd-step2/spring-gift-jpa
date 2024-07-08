package gift.service;

import gift.config.JwtProvider;
import gift.domain.member.Member;
import gift.exception.LoginException;
import gift.exception.MemberNotFoundException;
import gift.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public void addMember(Member member) {
        memberRepository.save(member);
    }

    public String login(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password)
            .orElseThrow(LoginException::new);

        return jwtProvider.create(member);
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }

}
