package gift.Service;

import gift.Exception.ForbiddenException;
import gift.Model.Member;
import gift.Model.RequestMember;
import gift.Repository.MemberRepository;
import gift.Util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;


    public MemberService(MemberRepository memberRepository, JwtUtil jwtUtil){
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void signUpUser(RequestMember requestMember){
        Member member  = new Member(requestMember.email(), requestMember.password());
        memberRepository.save(member);
    }

    public String loginUser(RequestMember requestMember) throws ForbiddenException {
        Member member = memberRepository.findByEmail(requestMember.email()).orElseThrow(() -> new NoSuchElementException("매칭되는 멤버가 없습니다."));
        String temp = member.getPassword();
        if (!(temp.equals(requestMember.password())))
            throw new ForbiddenException("잘못된 로그인입니다");

        return jwtUtil.generateToken(member);
    }

    public Member getUserByToken(String token) {
        return memberRepository.findByEmail(jwtUtil.getSubject(token)).orElseThrow(()-> new NoSuchElementException("매칭되는 멤버가 없습니다"));
    }
}
