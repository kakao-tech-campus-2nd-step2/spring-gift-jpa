package gift.domain.controller;

import gift.domain.dto.UserRequestDto;
import gift.domain.service.UserService;
import gift.global.response.SuccessResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserRequestDto requestDto) {
        return SuccessResponse.ok(userService.registerUser(requestDto).token(), "token");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserRequestDto requestDto) {
        return SuccessResponse.ok(userService.loginUser(requestDto).token(), "token");
    }
}
