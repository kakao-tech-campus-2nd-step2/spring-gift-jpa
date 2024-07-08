package gift.Login.controller;

import gift.Login.model.Member;
import gift.Login.model.ResponseToken;
import gift.Login.model.Wishlist;
import gift.Login.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseToken> register(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Member existingMember = memberService.findMemberByEmail(email);
        if (existingMember != null) {
            return ResponseEntity.status(409).build();
        }

        Member newMember = memberService.registerMember(email, password);
        String token = memberService.generateToken(newMember);
        ResponseToken responseToken = new ResponseToken(token);
        return ResponseEntity.ok(responseToken);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseToken> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String token = memberService.login(email, password);

        if (token != null) {
            ResponseToken responseToken = new ResponseToken(token);
            return ResponseEntity.ok(responseToken);
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }
}
