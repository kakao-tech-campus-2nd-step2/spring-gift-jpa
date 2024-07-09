package gift.member;

import gift.login.JwtTokenUtil;
import gift.login.LoginMember;
import gift.login.TokenResponseDto;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members")
public class MemberController {

    private final MemberDao memberDao;
    private final LogoutTokenDao logoutTokenDao;

    public MemberController(MemberDao memberDao, LogoutTokenDao logoutTokenDao) {
        this.memberDao = memberDao;
        this.logoutTokenDao = logoutTokenDao;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@RequestBody Member member) {
        Optional<Member> existMember = memberDao.findMember(member);
        if (!existMember.isPresent()) {
            memberDao.insertMember(member);
            String token = JwtTokenUtil.generateToken(member.getEmail());
            return ResponseEntity.ok(new TokenResponseDto(token));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 회원정보가 존재합니다");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member member) {
        Optional<Member> existMember = memberDao.findMember(member);
        if (existMember.isPresent()) {
            String token = JwtTokenUtil.generateToken(member.getEmail());
            return ResponseEntity.ok(new TokenResponseDto(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원정보가 존재하지 않습니다");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        logoutTokenDao.insertToken(token);
        return ResponseEntity.ok("로그아웃 되었습니다");
    }
}
