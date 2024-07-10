package gift.controller.api;

import gift.dto.request.MemberRequest;
import gift.dto.response.TokenResponse;
import gift.service.MemberService;
import gift.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final TokenService tokenService;

    public MemberController(MemberService memberService, TokenService tokenService) {
        this.memberService = memberService;
        this.tokenService = tokenService;
    }

    @PostMapping("/members/register")
    public ResponseEntity<TokenResponse> registerMember(@Valid @RequestBody MemberRequest request) {
        Long registeredMemberId = memberService.registerMember(request.email(), request.password());
        TokenResponse token = tokenService.generateToken(registeredMemberId);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/members/login")
    public ResponseEntity<TokenResponse> loginMember(@Valid @RequestBody MemberRequest request) {
        Long registeredMemberId = memberService.login(request.email(), request.password());
        TokenResponse token = tokenService.generateToken(registeredMemberId);
        return ResponseEntity.ok(token);
    }

}
