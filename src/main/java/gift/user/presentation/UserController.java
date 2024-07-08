package gift.user.presentation;

import gift.user.application.UserService;
import gift.user.domain.User;
import gift.user.domain.UserRegisterRequest;
import gift.user.exception.UserException;
import gift.util.CommonResponse;
import gift.util.ErrorCode;
import gift.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(new CommonResponse<>(null, "유저 등록이 정상적으로 완료되었습니다", true));
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(
            new CommonResponse<>(new AuthenticationResponse(token), "로그인이 정상적으로 완료되었습니다", true));

    }

    public static class AuthenticationResponse {

        private final String jwt;

        public AuthenticationResponse(String jwt) {
            this.jwt = jwt;
        }

        public String getJwt() {
            return jwt;
        }
    }
}
