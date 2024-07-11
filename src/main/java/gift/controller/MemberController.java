package gift.controller;

import gift.argumentresolver.LoginMember;
import gift.dto.MemberDTO;
import gift.dto.MemberPasswordDTO;
import gift.service.MemberService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok().body(memberService.register(memberDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok().body(memberService.login(memberDTO));
    }

    @PostMapping("/password")
    public ResponseEntity<Map<String, String>> changePassword(
        @LoginMember MemberDTO memberDTO,
        @Valid @RequestBody MemberPasswordDTO memberPasswordDTO
    ) {
        return ResponseEntity.ok().body(memberService.changePassword(memberDTO, memberPasswordDTO));
    }
}
