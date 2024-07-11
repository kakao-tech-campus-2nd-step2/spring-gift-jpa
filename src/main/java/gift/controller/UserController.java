package gift.controller;

import gift.domain.model.entity.User;
import gift.domain.model.dto.UserRequestDto;
import gift.domain.model.dto.TokenResponseDto;
import gift.domain.model.dto.UserResponseDto;
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
    public ResponseEntity<User> joinUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto response = userService.joinUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .header("Authorization", "Bearer " + response.getToken())
            .body(response.getUser());
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody UserRequestDto loginRequestDto) {
        String token = userService.loginUser(loginRequestDto);
        return ResponseEntity.ok(new TokenResponseDto(token));
    }
}
