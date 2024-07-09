package gift.controller;

import gift.dto.MemberDTO;
import gift.model.Member;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid MemberDTO memberDTO) {
        String token = memberService.register(memberDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return ResponseEntity.ok().headers(headers).body("{\"token\": \"" + token + "\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid MemberDTO memberDTO) {
        String token = memberService.login(memberDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return ResponseEntity.ok().headers(headers).body("{\"token\": \"" + token + "\"}");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.substring(7);
        memberService.logout(token);
        return ResponseEntity.ok().body("{\"message\": \"로그아웃 되었습니다.\"}");
    }
}
