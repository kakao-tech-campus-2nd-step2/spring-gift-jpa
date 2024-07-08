package gift.member.controller;

import gift.member.domain.TokenDTO;
import gift.member.domain.Member;
import gift.member.service.MemberService;
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
    public ResponseEntity<?> register(@RequestBody Member member) {
        TokenDTO token = memberService.register(member);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member member) {
        TokenDTO token = memberService.login(member);
        return ResponseEntity.ok(token);
    }

}
