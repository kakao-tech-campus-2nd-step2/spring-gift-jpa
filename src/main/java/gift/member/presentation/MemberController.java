package gift.member.presentation;

import gift.auth.TokenService;
import gift.member.application.MemberResponse;
import gift.member.application.MemberService;
import gift.member.presentation.request.MemberJoinRequest;
import gift.member.presentation.request.MemberLoginRequest;
import gift.member.presentation.request.MemberUpdateRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final TokenService tokenService;

    public MemberController(MemberService memberService, TokenService tokenService) {
        this.memberService = memberService;
        this.tokenService = tokenService;
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestBody MemberJoinRequest request
    ) {
        String email = memberService.join(request.toCommand());
        String token = tokenService.createToken(email);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody MemberLoginRequest request
    ) {
        String email = memberService.login(request.toCommand());
        String token = tokenService.createToken(email);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberResponse> findByEmail(
            @PathVariable("email") String email
    ) {
        return ResponseEntity.ok(memberService.findByEmail(email));
    }

    @GetMapping()
    public ResponseEntity<List<MemberResponse>> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @PutMapping("/{email}")
    public void update(
            @PathVariable("email") String email,
            @RequestBody MemberUpdateRequest request
    ) {
        memberService.update(request.toCommand(email));
    }

    @DeleteMapping("/{email}")
    public void delete(
            @PathVariable("email") String email
    ) {
        memberService.delete(email);
    }
}

