package gift.controller;

import gift.dto.LoginResponseDTO;
import gift.dto.UserRequestDTO;
import gift.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRequestDTO userRequestDTO) {
        memberService.register(userRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 사용자 로그인
    @PostMapping("/login/token")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserRequestDTO userRequest) {
        try {
            LoginResponseDTO response = memberService.authenticate(userRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
