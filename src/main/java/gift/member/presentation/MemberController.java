package gift.member.presentation;

import gift.auth.TokenService;
import gift.member.application.MemberResponse;
import gift.member.application.MemberService;
import gift.member.presentation.request.MemberEmailUpdateRequest;
import gift.member.presentation.request.MemberJoinRequest;
import gift.member.presentation.request.MemberLoginRequest;
import gift.member.presentation.request.MemberPasswordUpdateRequest;
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
        Long memberId = memberService.join(request.toCommand());
        String token = tokenService.createToken(memberId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody MemberLoginRequest request
    ) {
        Long memberId = memberService.login(request.toCommand());
        String token = tokenService.createToken(memberId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findById(
            @PathVariable("id") Long memberId
    ) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }

    @GetMapping()
    public ResponseEntity<List<MemberResponse>> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @PutMapping("/{id}/email")
    public void updateEmail(
            @PathVariable("id") Long memberId,
            @RequestBody MemberEmailUpdateRequest request
    ) {
        memberService.updateEmail(request.toCommand(memberId));
    }

    @PutMapping("/{id}/password")
    public void updatePassword(
            @PathVariable("id") Long memberId,
            @RequestBody MemberPasswordUpdateRequest request
    ) {
        memberService.updatePassword(request.toCommand(memberId));
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long memberId
    ) {
        memberService.delete(memberId);
    }
}

