package gift.member.controller;

import gift.member.dto.MemberRequest;
import gift.member.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberRequest memberRequest) {
        String token = memberService.register(memberRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return ResponseEntity.ok().headers(headers).body("{\"token\": \"" + token + "\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberRequest memberRequest) {
        String token = memberService.login(memberRequest.getEmail(), memberRequest.getPassword());
        if (token != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            return ResponseEntity.ok().headers(headers).body("{\"token\": \"" + token + "\"}");
        }
        return ResponseEntity.status(403).body("{\"error\": \"존재하지 않는 이메일이거나 비밀번호가 잘못되었습니다.\"}");
    }

}
