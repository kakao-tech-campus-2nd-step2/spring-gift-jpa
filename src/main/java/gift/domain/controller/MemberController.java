package gift.domain.controller;

import gift.domain.controller.apiResponse.MemberLoginApiResponse;
import gift.domain.controller.apiResponse.MemberRegisterApiResponse;
import gift.domain.dto.request.MemberRequest;
import gift.domain.service.MemberService;
import gift.global.apiResponse.SuccessApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<MemberRegisterApiResponse> registerMember(@RequestBody MemberRequest requestDto) {
        return SuccessApiResponse.ok(new MemberRegisterApiResponse(HttpStatus.OK, memberService.registerUser(requestDto).token()));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginApiResponse> loginMember(@RequestBody MemberRequest requestDto) {
        return SuccessApiResponse.ok(new MemberLoginApiResponse(HttpStatus.OK, memberService.loginUser(requestDto).token()));
    }
}
