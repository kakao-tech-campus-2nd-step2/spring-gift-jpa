package gift.controller;

import gift.model.Member;
import gift.model.MemberDTO;
import gift.security.JwtUtil;
import gift.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class AuthController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public AuthController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MemberDTO memberDTO) {
        memberService.createMember(memberDTO);
        Member savedMember = memberService.findMemberByCredentials(memberDTO.email(), memberDTO.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberDTO memberDTO) {
        Member member = memberService.findMemberByCredentials(memberDTO.email(),
            memberDTO.password());
        String token = jwtUtil.generateToken(member.getId());
        return ResponseEntity.ok("Bearer " + token);
    }
}
