package gift.controller;

import gift.model.JwtUtil;
import gift.model.Member;
import gift.dao.MemberDao;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Objects;

@RestController
@RequestMapping("/member")
public class MemberController {
    long id = 0L;

    Interceptor interceptor = new Interceptor();
    JwtUtil jwtUtil = new JwtUtil();
    private final MemberDao MemberDao;

    public MemberController(gift.dao.MemberDao memberDao) {
        MemberDao = memberDao;
    }

    @PostMapping("/signin")
    public String signin(@RequestBody Member member){
        if (MemberDao.selectMember(member.getEmail()) == null){
            id++;
            member.setId(id);
            MemberDao.insertMember(member);
            System.out.println("signin: " + member.getId() + " " + member.getEmail());
            String token = jwtUtil.createJwt(member.getId(), member.getEmail());
            return token;
        }
        else {
            throw new IllegalArgumentException("이미 가입한 이메일 입니다.");
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody Member member){
        if (MemberDao.selectMember(member.getEmail()) == null) {
            throw new IllegalArgumentException("이메일을 확인해주세요.");
        }
        Member loginMember = MemberDao.selectMember(member.getEmail());

        if(Objects.equals(member.getPassword(), loginMember.getPassword())){
            System.out.println("login: " + loginMember.getId() + " " + member.getEmail());
            String token = jwtUtil.createJwt(loginMember.getId(), member.getEmail());
            return token;
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
            System.out.println(id);
        System.out.println(id);
        return id;
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}