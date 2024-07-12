package gift.service;

import gift.model.CreateJwtToken;
import gift.model.Member;
import gift.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        else {
            throw new IllegalArgumentException("이미 가입한 이메일 입니다.");
        }
    }

    public String login(Member member){
        if(!memberRepository.existsByEmail(member.getEmail())){
            throw new IllegalArgumentException("이메일을 확인해주세요.");
        }
        Member loginMember = memberRepository.findByEmail(member.getEmail());
        return getTokenWhenCorrectPW(loginMember, member);
    }

    private String getTokenWhenCorrectPW(Member member, Member inputMember){
        if(member.equals(inputMember)){
            return createJwtToken.createJwt(member.getId(), member.getEmail());
        }
        else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
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

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}
