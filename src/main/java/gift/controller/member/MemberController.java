package gift.controller.member;

import gift.controller.auth.AuthController;
import gift.controller.auth.LoginRequest;
import gift.controller.auth.LoginResponse;
import gift.controller.auth.Token;
import gift.login.LoginMember;
import gift.service.AuthService;
import gift.service.MemberService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    public MemberController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers(@LoginMember LoginResponse member) {
        AuthController.validateAdmin(member);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findAll());
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@LoginMember LoginResponse member,
        @PathVariable UUID memberId) {
        AuthController.validateUserOrAdmin(member, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findById(memberId));
    }

    @PostMapping("/register")
    public ResponseEntity<Token> createMember(@RequestBody LoginRequest member) {
        memberService.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(member));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMember(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId, @RequestBody MemberRequest member) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.update(memberId, member));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        memberService.delete(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
