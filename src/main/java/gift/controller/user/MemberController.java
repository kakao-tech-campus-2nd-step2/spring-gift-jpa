package gift.controller.user;

import gift.global.auth.Authorization;
import gift.global.auth.Authenticate;
import gift.global.auth.LoginInfo;
import gift.controller.user.dto.MemberRequest;
import gift.controller.user.dto.MemberResponse.InfoResponse;
import gift.controller.user.dto.MemberResponse.LoginResponse;
import gift.model.member.Role;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<String> register(
        @RequestBody @Valid MemberRequest.Register request
    ) {
        memberService.register(request);
        return ResponseEntity.ok().body("User created successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @RequestBody @Valid MemberRequest.Login request
    ) {
        return ResponseEntity.ok(LoginResponse.from(memberService.login(request)));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return null;
    }

    @Authorization(role = Role.USER)
    @GetMapping("")
    public ResponseEntity<InfoResponse> getUser(@Authenticate LoginInfo loginInfo) {
        return ResponseEntity.ok(InfoResponse.from(memberService.getUser(loginInfo.memberId())));
    }

}
