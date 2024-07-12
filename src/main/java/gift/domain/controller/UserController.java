package gift.domain.controller;

import gift.domain.controller.apiResponse.UserLoginApiResponse;
import gift.domain.controller.apiResponse.UserRegisterApiResponse;
import gift.domain.dto.request.UserRequest;
import gift.domain.service.UserService;
import gift.global.apiResponse.SuccessApiResponse;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UserRegisterApiResponse> registerUser(@RequestBody UserRequest requestDto) {
        return SuccessApiResponse.ok(new UserRegisterApiResponse(HttpStatus.OK, userService.registerUser(requestDto).token()));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginApiResponse> loginUser(@RequestBody UserRequest requestDto) {
        return SuccessApiResponse.ok(new UserLoginApiResponse(HttpStatus.OK, userService.loginUser(requestDto).token()));
    }
}
