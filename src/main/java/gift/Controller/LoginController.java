package gift.Controller;

import gift.Model.User;
import gift.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/members/register")
    public ResponseEntity<?> register(@RequestBody User user){
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/members/login")
    public ResponseEntity<?> login(@RequestBody User user){
        return ResponseEntity.ok(userService.login(user));
    }
}
