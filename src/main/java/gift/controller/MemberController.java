package gift.controller;

import gift.domain.Member;
import gift.domain.Token;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Member member) {
        Token token = memberService.register(member);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Member member) {
        Token token = memberService.login(member);
        return ResponseEntity.ok(token);
    }

}
