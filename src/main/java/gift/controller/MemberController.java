package gift.controller;

import gift.domain.member.Member;
import gift.domain.member.MemberRequest;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public Member register(@Valid @RequestBody MemberRequest memberRequest) {
        return memberService.register(memberRequest);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody MemberRequest memberRequest) {
        return memberService.login(memberRequest);
    }
}
