package gift.main.controller;

import gift.main.dto.UserJoinRequest;
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
    public ResponseEntity<?> joinMember(@RequestBody UserJoinRequest userJoinDto, HttpServletResponse response) {
        String token = memberService.joinUser(userJoinDto);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", token);
        responseBody.put("message", "User registered successfully");
        responseBody.put("redirectUrl", "/spring-gift");
        // 응답 본문에 사용자 정보 혹은 성공 메시지 포함
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("redirectUrl", "/"));
}

    @PostMapping("/members/login")
    public ResponseEntity<?>


}
