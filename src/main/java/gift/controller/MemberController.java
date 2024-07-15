package gift.controller;

import gift.dto.MemberDTO;
import gift.service.JwtService;
import gift.util.LoginMember;
import gift.domain.Member;
import gift.service.MemberService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    private final JwtService jwtService;

    public MemberController(MemberService memberService, JwtService jwtService) {
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody MemberDTO memberDTO) {
        Member member = memberDTO.toEntity();
        Member savedMember = memberService.createMember(member);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwtService.generateToken(savedMember));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody MemberDTO memberDTO) {
        Member member = memberDTO.toEntity();
        Member foundMember = getValidMember(member);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwtService.generateToken(foundMember));
        return ResponseEntity.ok(response);
    }

    private Member getValidMember(Member member) {
        Member foundMember = memberService.getMemberByEmail(member.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!isPasswordValid(foundMember, member.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return foundMember;
    }

    private boolean isPasswordValid(Member foundMember, String password) {
        return foundMember.getPassword().equals(password);
    }

    @GetMapping("/profile")
    public ResponseEntity<Member> getProfile(@LoginMember Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member not found or unauthorized");
        }
        return ResponseEntity.ok(member);
    }
}
