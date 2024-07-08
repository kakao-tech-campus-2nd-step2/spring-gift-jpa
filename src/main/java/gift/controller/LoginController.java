package gift.controller;

import gift.auth.JwtService;
import gift.request.JoinRequest;
import gift.response.JoinResponse;
import gift.request.LoginRequest;
import gift.exception.InputException;
import gift.model.Member;
import gift.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final MemberService memberService;
    private final JwtService jwtService;

    public LoginController(MemberService memberService, JwtService jwtService) {
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    @PostMapping("/api/join")
    public ResponseEntity<JoinResponse> join(@RequestBody @Valid JoinRequest joinRequest,
        BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult.getAllErrors());
        }

        Member joinedMember = memberService.join(joinRequest.email(), joinRequest.password());
        jwtService.createToken(joinedMember, response);

        return ResponseEntity.ok(new JoinResponse(joinRequest.email(), "회원가입이 완료되었습니다."));
    }

    @PostMapping("/api/login")
    public void login(@RequestBody @Valid LoginRequest loginRequest,
        BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult.getAllErrors());
        }

        Member loginedMember = memberService.login(loginRequest.email(), loginRequest.password());
        jwtService.createToken(loginedMember, response);

    }

}
