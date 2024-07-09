package gift.doamin.user.controller;

import gift.doamin.user.service.AuthService;
import gift.doamin.user.dto.LoginForm;
import gift.doamin.user.dto.SignUpForm;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void signUp(@Valid @RequestBody SignUpForm signUpForm) {
        authService.signUp(signUpForm);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginForm loginForm) {
        String token = authService.login(loginForm);
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, token)
            .build();
    }
}
