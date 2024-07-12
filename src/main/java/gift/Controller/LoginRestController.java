package gift.Controller;

import gift.Model.DTO.MemberDTO;
import gift.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {
    private final UserService userService;

    public LoginRestController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/members/register")
    public ResponseEntity<?> register(@RequestBody MemberDTO memberDTO){
        return ResponseEntity.ok(userService.register(memberDTO));
    }

    @PostMapping("/members/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO){
        return ResponseEntity.ok(userService.login(memberDTO));
    }
}
