package gift.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class APIAuthController {
    @GetMapping("/sign-up")
    public String signUp() {
        return "가입 완료";
    }

    @GetMapping("/login")
    public String login() {
        return "로그인 완료";
    }
}
