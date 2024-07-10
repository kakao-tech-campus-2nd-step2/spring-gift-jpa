package gift.member;

import gift.token.Token;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<Token> register(@Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(new Token(memberService.register(memberDTO)));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(new Token(memberService.login(memberDTO)));
    }
}
