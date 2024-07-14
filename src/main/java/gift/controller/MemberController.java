package gift.controller;

import gift.entity.MemberEntity;
import gift.domain.MemberDTO;
import gift.service.JwtUtil;
import gift.service.MemberService;
import gift.service.MemberServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody MemberDTO memberDTO) {
        MemberServiceStatus status = memberService.save(memberDTO);

        if (status == MemberServiceStatus.EMAIL_ALREADY_EXISTS) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("error", "Email already exists"));
        }

        return ResponseEntity.ok().body(Collections.singletonMap("message", "Success"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody MemberDTO memberDTO) {
        MemberEntity authenticatedMember = memberService.authenticateToken(memberDTO);

        if (authenticatedMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid email or password"));
        }

        Long userId = authenticatedMember.getId();

        // 토큰 생성 및 응답
        String token = jwtUtil.generateToken(authenticatedMember.getEmail(), userId);
        Map<String, String> responseBody = Collections.singletonMap("token", token);
        return ResponseEntity.ok().body(responseBody);
    }

}