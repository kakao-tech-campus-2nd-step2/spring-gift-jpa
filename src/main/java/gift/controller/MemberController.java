package gift.controller;

import gift.dto.JoinMemberDto;
import gift.dto.LoginDto;
import gift.service.MemberService;
import gift.vo.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberController {

    private final MemberService service;
    private final MemberService memberService;

    public MemberController(MemberService service, MemberService memberService) {
        this.service = service;
        this.memberService = memberService;
    }

    /**
     *
     * @param loginDto LoginDto {email, password}
     * @param model Model
     * @return JSON { "token" : ""}
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(LoginDto loginDto, Model model) {
        Member member = loginDto.toUser();
        String token = service.login(member);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/join")
    public ResponseEntity<Map<String, String>>  join(JoinMemberDto memberDto, Model model) {
        String token = service.join(memberDto.toMember());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

}
