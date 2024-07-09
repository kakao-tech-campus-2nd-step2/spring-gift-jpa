package gift.service;

import gift.domain.Member;
import gift.domain.MemberRequest;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private JwtService jwtService;

    public MemberService(MemberRepository memberRepository,JwtService jwtService){
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
    }

    public String join(MemberRequest memberRequest) {
        if(!memberRepository.existsById(memberRequest.id())){
            memberRepository.save(new Member(memberRequest.id(),memberRequest.password()));
            return jwtService.createJWT(memberRequest.id());
        }
        throw new NoSuchElementException("이미 존재하는 회원입니다.");
    }

    public String login(MemberRequest memberRequest) {
        Member dbMember = memberRepository.findById(memberRequest.id())
                .orElseThrow(() -> new NoSuchElementException("로그인에 실패했습니다 다시 시도해주세요"));
        if(!memberRequest.password().equals(dbMember.getPassword())){
            throw new NoSuchElementException("로그인에 실패하였습니다. 다시 시도해주세요");
        }
        else{
            String jwt = jwtService.createJWT(memberRequest.id());
            return jwt;
        }
    }
}
