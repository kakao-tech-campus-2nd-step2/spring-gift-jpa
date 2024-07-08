package gift.user;

import gift.auth.JwtUtil;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserApiController {

    private static final Logger logger = Logger.getLogger(UserApiController.class.getName());
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserApiController(UserService userService,JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil=jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        String password = user.getPassword();
        String email = user.getEmail();
        logger.info("회원가입 시도: " + email);
        try {
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일과 비밀번호는 비어있으면 안됩니다.");
            }
            if(!userService.registerUser(user)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하는 이메일입니다.");
            }
            logger.info("회원가입 완료: " + email);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            logger.severe("회원가입 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("잘못된 접근입니다.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        logger.info("로그인 시도 중: " + email);
        try {
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일과 비밀번호는 빈칸이면 안됩니다.");
            }
            String token = jwtUtil.generateToken(user);
            if (token != null) {
                String response = "access-token: " + token;
                logger.info("로그인 완료: " + email);
                return ResponseEntity.ok().body(response);
            } else {
                logger.warning("토큰 생성 실패: " + email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰 생성 실패");
            }
        } catch (Exception e) {
            logger.severe("Failed to login user due to exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("없는 유저입니다.");
        }
    }
}
