package gift.domain;

import gift.controller.TokenInterceptor;
import gift.dao.MemberDao;
import gift.model.CreatJwtToken;
import gift.model.Member;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import java.util.Objects;
import java.util.Optional;

@Component
public class MemberDomain {
    TokenInterceptor interceptor = new TokenInterceptor();
    CreatJwtToken jwtUtil = new CreatJwtToken();
    private final MemberDao memberDao;

    public MemberDomain(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public String signin(Member member){
        Optional<Member> memberOptional = Optional.ofNullable(memberDao.selectMember(member.getEmail()));
        if (!memberOptional.isPresent()) {
            memberDao.insertMember(member);
            return jwtUtil.createJwt(member.getId(), member.getEmail());
        }
        else {
            throw new IllegalArgumentException("이미 가입한 이메일 입니다.");
        }
    }

    public String login(Member member){
        if (memberDao.selectMember(member.getEmail()) == null) {
            throw new IllegalArgumentException("이메일을 확인해주세요.");
        }
        Member loginMember = memberDao.selectMember(member.getEmail());

        if(Objects.equals(member.getPassword(), loginMember.getPassword())){
            return jwtUtil.createJwt(loginMember.getId(), member.getEmail());
        }
        else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 토큰으로 멤버 id 가져옴
    @GetMapping("/getMemberId")
    public Long getIdByToken(HttpServletRequest request) throws AuthenticationException {
        Long id = 0L;
        Claims claims = interceptor.getClaims(request);
        id = claims.get("id", Long.class);
        return id;
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}
