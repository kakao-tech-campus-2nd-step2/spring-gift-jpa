package gift.member.controller;

import gift.member.model.Member;
import gift.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public Member register(@RequestParam String email, @RequestParam String password) {
        return memberService.register(email, password);
    }

    @PostMapping("/login")
    public Member login(@RequestParam String email, @RequestParam String password) {
        return memberService.login(email, password);
    }

    @GetMapping("/{member_id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.findById(id).orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }

    @PutMapping("/{product_id}/email")
    public Member updateEmail(@PathVariable Long id, @RequestParam String newEmail) {
        return memberService.updateEmail(id, newEmail);
    }

    @PutMapping("/{product_id}/password")
    public Member updatePassword(@PathVariable Long id, @RequestParam String newPassword) {
        return memberService.updatePassword(id, newPassword);
    }

    @DeleteMapping("/{member_id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}