package gift.controller;

import gift.dto.UserRequest;
import gift.dto.UserResponse;
import gift.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {
        String token = userService.authenticateUser(userRequest.getEmail(),
            userRequest.getPassword());
        UserResponse response = new UserResponse(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        userService.registerUser(userRequest.getEmail(), userRequest.getPassword());
        String token = userService.authenticateUser(userRequest.getEmail(),
            userRequest.getPassword());
        UserResponse response = new UserResponse(token);
        return ResponseEntity.ok(response);
    }
}
