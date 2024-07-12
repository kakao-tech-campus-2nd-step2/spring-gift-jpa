package gift.domain.controller;

import gift.domain.dto.UserLoginResponseDto;
import gift.domain.dto.UserRegisterResponseDto;
import gift.domain.dto.UserRequestDto;
import gift.domain.service.UserService;
import gift.global.response.SuccessResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> registerUser(@RequestBody UserRequestDto requestDto) {
        return SuccessResponse.ok(new UserRegisterResponseDto(HttpStatus.OK, userService.registerUser(requestDto).token()));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody UserRequestDto requestDto) {
        return SuccessResponse.ok(new UserLoginResponseDto(HttpStatus.OK, userService.loginUser(requestDto).token()));
    }
}
