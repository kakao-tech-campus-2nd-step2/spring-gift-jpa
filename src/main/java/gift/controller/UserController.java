package gift.controller;

import gift.entity.UserDTO;
import gift.service.UserService;
import gift.util.UserUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserUtility userUtility;
    private final UserService userService;

    @Autowired
    public UserController(UserUtility userUtility, UserService userService) {
        this.userUtility = userUtility;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid UserDTO form) {
        String accessToken = userService.signup(form);
        return ResponseEntity.ok().body(userUtility.accessTokenToObject(accessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserDTO form) {
        String accessToken = userService.login(form);
        return ResponseEntity.ok().body(userUtility.accessTokenToObject(accessToken));
    }
}
