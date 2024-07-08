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

    @GetMapping("/users/{email}")
    public ResponseEntity<Member> getUserByEmail(@PathVariable("email") String email) {
        Member member = memberService.getUserByEmail(email);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Member> getUserById(@PathVariable("id") Long id) {
        Member member = memberService.getUserById(id);
        return ResponseEntity.ok().body(member);
    }
}
