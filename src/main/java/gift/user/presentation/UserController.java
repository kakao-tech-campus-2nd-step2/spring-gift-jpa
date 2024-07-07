package gift.user.presentation;

import gift.user.application.UserService;
import gift.user.domain.User;
import gift.user.domain.UserRegisterRequest;
import gift.util.CommonResponse;
import gift.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(new CommonResponse<>(null, "유저 등록이 정상적으로 완료되었습니다", true));
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
        if (authenticatedUser != null) {
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(
                new CommonResponse<>(new AuthenticationResponse(token), "로그인이 정상적으로 완료되었습니다", true));
        } else {
            return ResponseEntity.status(401).body(new CommonResponse<>(null, "로그인에 실패하였습니다", false));
        }
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
