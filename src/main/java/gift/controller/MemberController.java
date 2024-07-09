package gift.controller;

import gift.domain.MemberRequest;
import gift.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(
            @RequestParam("id") String id,
            @RequestParam("password") String password
    ) {
        MemberRequest memberRequest = new MemberRequest(id,password);
        return memberService.join(memberRequest);
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestParam("id") String id,
            @RequestParam("password") String password
    ) {
        MemberRequest memberRequest = new MemberRequest(id,password);
        return memberService.login(memberRequest);
    }

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(
            @RequestParam("id") String id,
            @RequestParam("passwd") String password
    ){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("changePassword is not allowed");
    }

    @PostMapping("/findPassword")
    public ResponseEntity findPassword(
            @RequestParam("id") String id,
            @RequestParam("passwd") String password
    ){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("findPassword is not allowed");
    }
}
