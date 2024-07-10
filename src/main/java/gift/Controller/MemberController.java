package gift.Controller;

import gift.Model.RequestMember;
import gift.Model.ResponseLoginDTO;
import gift.Service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;


    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody RequestMember member){
        memberService.signUpUser(member);
        return "signUp successed";
    }


    @PostMapping("/login")
    public ResponseLoginDTO loginUser(@RequestBody RequestMember member) {
        return new ResponseLoginDTO(memberService.loginUser(member));
    }

}
