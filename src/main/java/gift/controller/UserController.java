package gift.controller;

import gift.dto.user.UserRequestDto;
import gift.dto.user.UserResponseDto;
import gift.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto userRequest) {
        return new ResponseEntity<>(userService.registerUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid UserRequestDto userRequest) {
        return new ResponseEntity<>(userService.loginUser(userRequest), HttpStatus.OK);
    }

}
