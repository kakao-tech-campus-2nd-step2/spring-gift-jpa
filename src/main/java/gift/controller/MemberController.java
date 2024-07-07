package gift.controller;

import gift.dto.member.MemberRequest;
import gift.dto.member.MemberResponse;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 회원가입 (회원 추가)
    @PostMapping("/register")
    public ResponseEntity<MemberResponse> register(@Valid @RequestBody MemberRequest memberDTO) {
        MemberResponse registeredMember = memberService.registerMember(memberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredMember);
    }

    // 로그인 (회원 검증)
    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@RequestBody MemberRequest memberDTO) {
        MemberResponse loggedInMember = memberService.loginMember(memberDTO);
        return ResponseEntity.ok(loggedInMember);
    }
}
