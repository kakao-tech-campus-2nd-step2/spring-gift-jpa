package gift.controller;

import gift.dto.MemberDTO;
import gift.entity.Member;
import gift.response.AuthResponse;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    MemberService userService;

    public MemberController(MemberService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody MemberDTO memberDTO) {
        Member member = memberDTO.toEntity();
        userService.generateUser(member);

        return new ResponseEntity<>("User 생성 완료", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody MemberDTO memberDTO) {
        Member member = memberDTO.toEntity();
        AuthResponse response = new AuthResponse(userService.authenticateUser(member));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
