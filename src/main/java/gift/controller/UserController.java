package gift.controller;

import gift.dto.LoginUser;
import gift.entity.User;
import gift.service.LoginMember;
import gift.service.UserService;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signupRendering() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginRendering() {
        return "login";
    }

    @Description("임시 확인용 html form. service x ")
    @GetMapping("/user-info")
    public ResponseEntity<List<User>> userInfoRendering() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/members")
    public ResponseEntity<Map<String, String>> register(@ModelAttribute("user") User user) {
        return userService.signUp(user);
    }

    @PostMapping("/members/login")
    public ResponseEntity<String> login(@ModelAttribute("user") User user) {
        return userService.login(user);
    }

    @PostMapping("/members/token-login")
    public ResponseEntity<String> tokenLogin(@LoginMember LoginUser loginUser) {
        return userService.tokenLogin(loginUser);
    }

}
