package gift.controller;

import gift.model.Member;
import gift.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable("email") String email) {
        Member member = memberService.getMemberByEmail(email);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id) {
        Member member = memberService.getMemberById(id);
        return ResponseEntity.ok().body(member);
    }
}
