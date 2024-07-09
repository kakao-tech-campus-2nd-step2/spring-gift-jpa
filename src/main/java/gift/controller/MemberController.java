package gift.controller;

import gift.dto.request.MemberRequest;
import gift.domain.Member;
import gift.service.MemberService;
import gift.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @Autowired
    public MemberController(MemberService memberService, TokenService tokenService) {
        this.memberService = memberService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody MemberRequest memberRequest) {
        Member member = memberService.register(memberRequest);
        String token = tokenService.saveToken(member);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody MemberRequest memberRequest) {
        Member member = memberService.authenticate(memberRequest);
        String token = tokenService.saveToken(member);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

}
