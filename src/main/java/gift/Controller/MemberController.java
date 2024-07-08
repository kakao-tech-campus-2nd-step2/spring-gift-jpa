package gift.Controller;

import gift.model.Member;
import gift.model.MemberDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Objects;

@RestController
@RequestMapping("/member")
public class MemberController {
    long id = 0L;

    private final MemberDao MemberDao;

    public MemberController(gift.model.MemberDao memberDao) {
        MemberDao = memberDao;
    }

    @PostMapping("/signin")
    public String signin(@RequestBody Member member){
        if (MemberDao.selectMember(member.getEmail()) == null){
            id++;
            member.setId(id);
            MemberDao.insertMember(member);
            System.out.println("signin: " + member.getId() + " " + member.getEmail());
            String token = JwtUtil.createJwt(member.getId(), member.getEmail());
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
            String token = JwtUtil.createJwt(loginMember.getId(), member.getEmail());
            return token;
        }
        else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    static String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    // 토큰으로 멤버 id 가져옴
    @GetMapping("/getMemberId")
    public Long getIdByToken(HttpServletRequest request) throws AuthenticationException {
        Long id = 0L;
        // 요청 헤더에서 Authorization 헤더 값을 가져옴
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Authorization 헤더에서 Bearer 토큰을 추출
            String token = authHeader.substring(7);
            System.out.println(token);
            // 토큰을 파싱하여 클레임(Claims)을 추출
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            id = claims.get("id", Long.class);
            System.out.println(id);
        }
        else{
            throw new AuthenticationException("헤더 혹은 토큰이 유효하지 않습니다.");
        }
        System.out.println(id);
        return id;
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException e) {
        return e.getMessage();
    }
}
