package gift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/member")
public class MemberViewController {

    /**
     * 회원가입 폼을 반환
     */
    @GetMapping("/register")
    public String registerMemberForm() {
        return "memberRegister";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
