package gift.controller;

import gift.dto.UserDto;
import gift.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("")
    public String main() {
        return "auth/index";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "auth/sign-up";
    }

    @PostMapping("/save")
    public ResponseEntity<TokenResponse> save(@ModelAttribute UserDto.Request request) {
        String token = authService.save(request);
        Map<String, String> saveResult = new HashMap<>();

        if (token != null) {

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new TokenResponse(token));
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(new TokenResponse(""));
    }

    @PostMapping("/user/login")
    public ResponseEntity<TokenResponse> login(@ModelAttribute UserDto.Request request, HttpSession session) {
        UserDto loginResult = authService.login(request);

        if (loginResult != null) {

            String token = authService.generateToken(request.getEmail(), request.getPassword());
            session.setAttribute("token", token);

            return ResponseEntity.ok()
                    .header("Authorization", "Basic" + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new TokenResponse(token));
        }

        return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(new TokenResponse(""));
    }

    class TokenResponse {
        private String token;

        public TokenResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
