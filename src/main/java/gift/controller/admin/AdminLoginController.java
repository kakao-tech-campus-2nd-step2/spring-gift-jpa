package gift.controller.admin;

import gift.controller.dto.request.SignInRequest;
import gift.controller.dto.response.TokenResponse;
import gift.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminLoginController {
    private final AuthService authService;

    public AdminLoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute SignInRequest request, HttpServletResponse response) {
        TokenResponse tokenResponse  = TokenResponse.from(authService.signIn(request));
        String token = tokenResponse .accessToken();
        ResponseCookie cookie = ResponseCookie.from("Authorization", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60) // 24시간 유효기간
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return "redirect:/admin";
    }
}
