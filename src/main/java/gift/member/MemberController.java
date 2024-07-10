package gift.member;

import gift.exception.AlreadyExistMember;
import gift.exception.NotFoundMember;
import gift.login.JwtTokenUtil;
import gift.login.TokenResponseDto;
import gift.logout.LogoutTokenService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;
    private final LogoutTokenService logoutTokenService;

    public MemberController(MemberService memberService, LogoutTokenService logoutTokenService) {
        this.memberService = memberService;
        this.logoutTokenService = logoutTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@RequestBody Member member) throws AlreadyExistMember {
//        Optional<Member> existMember = memberService.getMember(member);
//        if (!existMember.isPresent()) {
//            memberService.postMember(member);
//            String token = JwtTokenUtil.generateToken(member.getEmail());
//            return ResponseEntity.ok(new TokenResponseDto(token));
//        } else {
//            throw new AlreadyExistMember("이미 회원정보가 존재합니다");
//        }
        return memberService.register(member);

    } // ㅋㅋ 회원가입, 로그인, 로그아웃만 하면 되자나 완전 럭키자나~~

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member member) throws NotFoundMember {
//        Optional<Member> existMember = memberService.getMember(member);
//        if (existMember.isPresent()) {
//            String token = JwtTokenUtil.generateToken(member.getEmail());
//            return ResponseEntity.ok(new TokenResponseDto(token));
//        } else {
//            throw new NotFoundMember("회원정보가 존재하지 않습니다");
//        }
        return memberService.login(member);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        logoutTokenService.postToken(token);
        return ResponseEntity.ok("로그아웃 되었습니다");
    }
}
