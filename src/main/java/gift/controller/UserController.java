package gift.controller;

import gift.model.user.User;
import gift.model.user.UserRequest;
import gift.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 로그인
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(@RequestBody UserRequest userRequest) {
        try {
            String token = userService.login(userRequest.getEmail(), userRequest.getPassword())
                    .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));
            return ResponseEntity.ok(Map.of("accessToken", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "이메일 혹은 패스워드가 틀렸습니다."));
        }
    }

    // 회원가입
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
        User user = new User(userRequest.getEmail(), userRequest.getPassword());
        boolean registered = userService.register(user);
        if (registered) {
            return ResponseEntity.ok("회원가입이 정상적으로 완료되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패");
    }
}
