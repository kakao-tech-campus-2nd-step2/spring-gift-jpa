package gift.controller;

import gift.domain.model.User;
import gift.domain.model.UserRequestDto;
import gift.domain.model.TokenResponseDto;
import gift.domain.model.UserResponseDto;
import gift.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
<<<<<<< HEAD
    public ResponseEntity<User> joinUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto response = userService.joinUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .header("Authorization", "Bearer " + response.getToken())
            .body(response.getUser());
=======
    public ResponseEntity<UserResponseDto> joinUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto response = userService.joinUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
>>>>>>> c87bd7d (refactor: schema.sql 삭제)
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody UserRequestDto loginRequestDto) {
        String token = userService.loginUser(loginRequestDto);
        return ResponseEntity.ok(new TokenResponseDto(token));
    }
}
