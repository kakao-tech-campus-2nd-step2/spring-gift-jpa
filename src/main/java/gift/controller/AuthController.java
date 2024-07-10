package gift.controller;

import gift.dto.UserDto;
import gift.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

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
    public String save(@ModelAttribute UserDto.Request request) {

        String token = authService.save(request);
        return token;
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserDto.Request request) throws AuthenticationException {

        authService.login(new UserDto(request.getId(), request.getEmail(), request.getPassword()));
        String token = authService.generateToken(request.getEmail(), request.getPassword());

        return token;

    }
}
