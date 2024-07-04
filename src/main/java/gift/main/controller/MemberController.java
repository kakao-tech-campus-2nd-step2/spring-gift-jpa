package gift.main.controller;

import gift.main.dto.UserJoinRequest;
import gift.main.dto.UserLoginRequest;
import gift.main.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members/register")
    public ResponseEntity<?> joinMember(@RequestBody UserJoinRequest userJoinRequest, HttpServletResponse response) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", memberService.joinUser(userJoinRequest));
        responseBody.put("redirectUrl", "/spring-gift");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("redirectUrl", "/"));
}

    @PostMapping("/members/login")
    public ResponseEntity<?> loinMember(@RequestBody UserLoginRequest userloginDto, HttpServletResponse response) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", memberService.loginUser(userloginDto));
        responseBody.put("redirectUrl", "/spring-gift");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("redirectUrl", "/"));

    }
}
