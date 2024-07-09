package gift.Login.controller;

import gift.Login.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        memberService.registerMember(email, password);
        return ResponseEntity.ok(memberService.login(email, password));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String token = memberService.login(email, password);

        if (token == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        return ResponseEntity.ok(token);
    }
}
