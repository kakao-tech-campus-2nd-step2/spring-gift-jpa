package gift.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그인 컨트롤러 클래스
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 로그인 메서드
     *
     * @param loginDTO 로그인 정보
     * @return 토큰 정보
     */
    @PostMapping
    public ResponseEntity<TokenDTO> Login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(loginService.Login(loginDTO));
    }

    /**
     * 회원가입 메서드
     *
     * @param loginDTO 회원가입 정보
     * @return 토큰 정보
     */
    @PostMapping("/signup")
    public ResponseEntity<TokenDTO> SignUp(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(loginService.SignUp(loginDTO));
    }
}
