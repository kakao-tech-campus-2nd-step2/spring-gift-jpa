package gift.Service;

import gift.Exception.ForbiddenException;
import gift.Model.Member;
import gift.Model.RequestMember;
import gift.Repository.MemberRepository;
import gift.Util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;


    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil){
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public void signUpUser(RequestMember requestMember){
        Member member  = new Member(requestMember.email(), requestMember.password());
        memberRepository.save(member);
    }

    public String loginUser(RequestMember requestMember) throws ForbiddenException {
        Member member = memberRepository.findByEmail(requestMember.email());
        String temp = member.getPassword();
        if (!(temp.equals(requestMember.password())))
            throw new ForbiddenException("잘못된 로그인입니다");

        return jwtUtil.generateToken(member);
    }

    public Member getUserByToken(String token) {
        Member member = memberRepository.findByEmail(jwtUtil.getSubject(token));
        return member;
    }
}
