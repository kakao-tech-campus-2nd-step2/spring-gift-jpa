package gift.controller;

import gift.domain.MemberDomain;
import gift.model.Member;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberDomain memberDomain;

    public MemberController(MemberDomain memberDomain) {
        this.memberDomain = memberDomain;
    }

    @PostMapping("/signin")
    public String signinController(@RequestBody Member member){
        return memberDomain.signin(member);
    }

    @PostMapping("/login")
    public String loginContorller(@RequestBody Member member){
        return memberDomain.login(member);
    }
}