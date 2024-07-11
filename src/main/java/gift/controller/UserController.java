package gift.controller;

import gift.dto.user.UserLoginRequest;
import gift.dto.user.UserRegisterRequest;
import gift.dto.user.UserResponse;
import gift.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRegisterRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid UserLoginRequest userRequest) {
        return ResponseEntity.ok(userService.loginUser(userRequest));
    }

}
