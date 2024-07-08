package gift.controller;

import gift.domain.User;
import gift.response.AuthResponse;
import gift.service.UserService;
import java.net.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        userService.generateUser(user);

        return new ResponseEntity<>("User 생성 완료", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user){
        AuthResponse response =new AuthResponse(userService.authenticateUser(user));
        return new ResponseEntity<>(response,HttpStatus.OK);

    }


}
