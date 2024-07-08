package gift.controller;

import gift.model.Login;
import gift.authService.LoginService;
import gift.model.Token;
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
     * @param login 로그인 정보
     * @return 토큰 정보
     */
    @PostMapping
    public ResponseEntity<Token> Login(@Valid @RequestBody Login login) {
        return ResponseEntity.ok(loginService.Login(login));
    }

    /**
     * 회원가입 메서드
     *
     * @param login 회원가입 정보
     * @return 토큰 정보
     */
    @PostMapping("/signup")
    public ResponseEntity<Token> SignUp(@Valid @RequestBody Login login) {
        return ResponseEntity.ok(loginService.SignUp(login));
    }
}
