package gift.api.member;

import gift.global.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid MemberRequest memberRequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        var accessToken = JwtUtil.generateAccessToken(memberService.register(memberRequest), memberRequest.email(), memberRequest.role());
        responseHeaders.set("Authorization", JwtUtil.generateHeaderValue(accessToken));
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody MemberRequest memberRequest, @RequestHeader("Authorization") String token) {
        memberService.login(memberRequest, token.split(" ")[1]);
        return ResponseEntity.ok().build();
    }
}
