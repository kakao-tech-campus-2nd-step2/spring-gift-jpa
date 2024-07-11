package gift.controller.member;

import gift.dto.member.TokenResponseDTO;
import gift.dto.member.MemberRequestDTO;
import gift.exception.InvalidPasswordException;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDTO> signUp(@RequestBody @Valid MemberRequestDTO memberRequestDTO) {
        TokenResponseDTO tokenResponseDTO = memberService.signUp(memberRequestDTO);

        return ResponseEntity.ok(tokenResponseDTO);
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid MemberRequestDTO memberRequestDTO) throws InvalidPasswordException {
        TokenResponseDTO tokenResponseDTO = memberService.login(memberRequestDTO);

        return ResponseEntity.ok(tokenResponseDTO);
    }
}
