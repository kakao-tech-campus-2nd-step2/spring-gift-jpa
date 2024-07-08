package gift.controller.user;

import gift.auth.Authorization;
import gift.auth.Authenticate;
import gift.auth.LoginInfo;
import gift.controller.user.dto.UserRequest;
import gift.controller.user.dto.UserResponse.InfoResponse;
import gift.controller.user.dto.UserResponse.LoginResponse;
import gift.model.user.Role;
import gift.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
        @RequestBody @Valid UserRequest.Register request
    ) {
        userService.register(request);
        return ResponseEntity.ok().body("User created successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @RequestBody @Valid UserRequest.Login request
    ) {
        return ResponseEntity.ok(LoginResponse.from(userService.login(request)));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return null;
    }

    @Authorization(role = Role.USER)
    @GetMapping("")
    public ResponseEntity<InfoResponse> getUser(@Authenticate LoginInfo loginInfo) {
        return ResponseEntity.ok(InfoResponse.from(userService.getUser(loginInfo.userId())));
    }

}
