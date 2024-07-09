package gift.controller;

import gift.model.dto.MemberRequestDto;
import gift.model.dto.MemberResponseDto;
import gift.service.AuthService;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    public MemberController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public MemberResponseDto register(
        @Valid @RequestBody MemberRequestDto memberRequestDto) {
        memberService.insertMember(memberRequestDto);
        return authService.getToken(memberRequestDto);
    }

    @PostMapping("/login")
    public MemberResponseDto login(
        @Valid @RequestBody MemberRequestDto memberRequestDto) {
        return authService.getToken(memberRequestDto);
    }
}
