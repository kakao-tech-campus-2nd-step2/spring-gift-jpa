package gift.service;

import gift.model.CreateJwtToken;
import gift.DTO.LogInMemberDTO;
import gift.model.Member;
import gift.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.NoSuchElementException;

@Service
public class MemberService {
    private final TokenInterceptor tokenInterceptor;
    private final CreateJwtToken createJwtToken;
    private final MemberRepository memberRepository;

    public MemberService(TokenInterceptor tokenInterceptor, CreateJwtToken createJwtToken, MemberRepository memberRepository) {
        this.tokenInterceptor = tokenInterceptor;
        this.createJwtToken = createJwtToken;
        this.memberRepository = memberRepository;
    }

    public String signin(Member member){
        if(!memberRepository.existsByEmail(member.getEmail())){
            memberRepository.save(member);
            return createJwtToken.createJwt(member.getId(), member.getEmail());
        }
        throw new IllegalArgumentException("이미 가입한 이메일 입니다.");
    }

    public String login(LogInMemberDTO member) {
        //1. 이메일 확인
        Member loginMember = comfirmEmail(member.getEmail());
        //2. 패스워드 확인
        if(loginMember.comfirmPW(member.getPassword())){
            //3. 토큰 발급
            return createJwtToken.createJwt(loginMember.getId(), loginMember.getEmail());
        }
        throw new IllegalStateException("로그인에 실패했습니다.");
    }

    private Member comfirmEmail(String email){
        if(!memberRepository.existsByEmail(email)){
            throw new IllegalStateException("이메일을 확인해주세요.");
        }
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("해당 멤버가 없습니다."));
    }

    // 토큰으로 멤버 id 가져옴
    public Long getIdByToken(HttpServletRequest request) throws AuthenticationException {
        Claims claims = tokenInterceptor.getClaims(request);
        return claims.get("id", Long.class);
    }

    public Member getMemberByAuth(HttpServletRequest request) throws AuthenticationException {
        Long memberId = getIdByToken(request);
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("해당 멤버가 없습니다."));
    }
}
