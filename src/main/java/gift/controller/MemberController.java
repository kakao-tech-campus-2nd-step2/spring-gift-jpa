package gift.controller;


import gift.dto.LoginResultDto;
import gift.dto.MemberDto;
import gift.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RequestMapping("/members")
@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewMember(@RequestBody MemberDto memberDto) {
        if(memberService.registerNewMember(memberDto)){
            String token = memberService.returnToken(memberDto);
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("already registered email");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberDto memberDto) {
        LoginResultDto loginResultDto = memberService.loginMember(memberDto);
        if(loginResultDto.isSuccess()){
            return ResponseEntity.ok().body(Collections.singletonMap("token", loginResultDto.getToken()));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/register")
    public String moveToRegister() {
        return "registerMember";
    }

    @GetMapping("/login")
    public String moveToLogin() {
        return "loginMember";
    }
}

