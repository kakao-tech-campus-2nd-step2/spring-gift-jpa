package gift.controller.member;

import gift.controller.auth.Token;
import gift.service.AuthService;
import gift.service.MemberService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    public MemberController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @GetMapping
    public List<MemberDto> getAllMembers() {
        return memberService.findAll();
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberDto> getMember(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.find(email));
    }

    @PostMapping("/register")
    public ResponseEntity<Token> signUp(@RequestBody MemberDto member) {
        memberService.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(member));
    }

    @PutMapping("/{email}")
    public ResponseEntity<MemberDto> putMember(@PathVariable String email, @RequestBody MemberDto member) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.update(email, member));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteMember(@PathVariable String email) {
        memberService.delete(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
