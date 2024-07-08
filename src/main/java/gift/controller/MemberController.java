package gift.controller;

import gift.dto.MemberRequest;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody MemberRequest memberRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid data");
        }
        String token = memberService.register(memberRequest);
        return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody MemberRequest memberRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid data");
        }
        try {
            String token = memberService.authenticate(memberRequest);
            return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(403).body("Forbidden");
        }
    }
}
