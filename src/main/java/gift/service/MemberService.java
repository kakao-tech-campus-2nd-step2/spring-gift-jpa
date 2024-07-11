package gift.service;

import gift.domain.member.Member;
import gift.domain.member.MemberRequest;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public Member register(MemberRequest memberRequest) {
        if (memberRepository.findByEmail(memberRequest.email()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 이메일입니다");
        }
        return memberRepository.insert(memberRequest);
    }

    public String  login(MemberRequest memberRequest) {
        Optional<Member> memberOptional = memberRepository.findByEmailAndPassword(memberRequest);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return jwtUtil.generateToken(member);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이메일 혹은 비밀번호가 일치하지 않습니다");
        }
    }
}
