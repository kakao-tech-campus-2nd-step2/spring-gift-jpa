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
    public Member getMemberById(@PathVariable Long member_id) {
        return memberService.findById(member_id).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    @PutMapping("/{product_id}/email")
    public Member updateEmail(@PathVariable Long member_id, @RequestParam String newEmail) {
        return memberService.updateEmail(member_id, newEmail);
    }

    @PutMapping("/{product_id}/password")
    public Member updatePassword(@PathVariable Long member_id, @RequestParam String newPassword) {
        return memberService.updatePassword(member_id, newPassword);
    }

    @DeleteMapping("/{member_id}")
    public void deleteMember(@PathVariable Long member_id) {
        memberService.deleteMember(member_id);
    }
}