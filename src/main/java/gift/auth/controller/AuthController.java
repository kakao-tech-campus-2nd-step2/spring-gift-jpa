package gift.auth.controller;

import gift.auth.dto.LoginReqDto;
import gift.auth.service.AuthService;
import gift.auth.token.AuthToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestHeader("Authorization") String header, @RequestBody LoginReqDto loginReqDto) {
        AuthToken token = authService.login(header, loginReqDto);
        return ResponseEntity.ok(token);
    }
}
