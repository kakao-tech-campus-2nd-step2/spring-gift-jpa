package gift.user;


import gift.jwt.JWTService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;
    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    //로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
        if(!userService.validateUser(loginDTO)){
            throw new IllegalArgumentException("이메일이 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }
        User user = userService.getUserByEmail(loginDTO.getEmail());
        String accessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new Token(accessToken));
    }

    //회원 가입 API
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody @Valid UserDTO userDTO){
        if(userService.checkIfDuplicatedEmail(userDTO.email)){
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        User user = userService.registerUser(userDTO);
        String accessToken = jwtService.generateAccessToken(user);
        return ResponseEntity.ok(new Token(accessToken));
    }

    @GetMapping("/token/check")
    public ResponseEntity<?> checkTokenValid(@RequestBody Token token) {
        boolean b = jwtService.tokenValidCheck(token.token);
        return ResponseEntity.ok(null);
    }


}
