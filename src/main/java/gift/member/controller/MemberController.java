package gift.member.controller;

import gift.member.model.Member;
import gift.member.service.MemberService;
import gift.member.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Member member) {
        // 회원 가입 처리
        member = memberService.register(member.email(), member.password());
        // 입력받은 정보로 부터 토큰 생성
        String token = tokenService.generateToken(member);
        // 응답으로 사용자 정보 맵 반환
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Member member) {
        Member loggedInMember = memberService.login(member.email(), member.password());
        String token = tokenService.generateToken(loggedInMember);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}