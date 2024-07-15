package gift.controller;

import gift.dto.TokenDto;
import gift.dto.UserDto;
import gift.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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
    public TokenDto save(@ModelAttribute UserDto.Request request) {
        return userService.save(request.getEmail(), request.getPassword());
    }

    @PostMapping("/user/login")
    public TokenDto login(@ModelAttribute UserDto.Request request) throws AuthenticationException {
        if (userService.login(request.getEmail(), request.getPassword())) {
            return userService.generateTokenDtoFrom(request.getEmail());
        }
        throw new AuthenticationException("로그인 실패");
    }
}
