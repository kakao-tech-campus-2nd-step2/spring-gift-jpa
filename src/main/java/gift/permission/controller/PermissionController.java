package gift.permission.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 로그인, 회원가입 view를 반환하는 컨트롤러
@Controller
public class PermissionController {

    @GetMapping
    public String getLoginPage() {
        return "html/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "html/registration";
    }
}
