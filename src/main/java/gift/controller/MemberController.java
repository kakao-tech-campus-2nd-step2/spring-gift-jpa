package gift.controller;

import gift.dto.TokenLoginRequestDTO;
import gift.dto.MemberDTO;
import gift.entity.Member;
import gift.service.LoginMember;
import gift.service.MemberService;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signupRendering() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginRendering() {
        return "login";
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> register(@ModelAttribute("memberDTO") MemberDTO memberDTO) {
        String token = memberService.signUp(memberDTO);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@ModelAttribute("memberDTO") MemberDTO memberDTO) {
        String token = memberService.login(memberDTO);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(responseBody);
    }

    @PostMapping("/token-login")
    public ResponseEntity<String> tokenLogin(@LoginMember TokenLoginRequestDTO tokenLoginRequestDTO) {
        memberService.tokenLogin(tokenLoginRequestDTO);
//      String token = tokenLoginRequestDTO.getToken();
        return ResponseEntity.status(HttpStatus.OK)
                .body("토큰 인증 성공");
    }

    @Description("임시 확인용 html form. service x ")
    @GetMapping("/user-info")
    public ResponseEntity<List<Member>> userInfoRendering() {
        List<Member> members = memberService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).
                body(members);
    }

}
