package gift.controller;

import gift.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestHeader("Authorization") String str) {
        var token = memberService.signUp(decodeToEmail(str), decodeToPassword(str));

        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String str) {
        var token = memberService.login(decodeToEmail(str), decodeToPassword(str));
        return ResponseEntity.ok(token);
    }

    private String decodeToEmail(String str) {
        String base64Credentials = str.substring("Basic ".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] values = credentials.split(":", 2);
        return values[0];
    }

    private String decodeToPassword(String str) {
        String base64Credentials = str.substring("Basic ".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] values = credentials.split(":", 2);
        return values[1];
    }
}
