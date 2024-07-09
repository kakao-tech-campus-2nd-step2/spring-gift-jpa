package gift.user.controller;

import gift.user.exception.ForbiddenException;
import gift.user.model.dto.LoginRequest;
import gift.user.model.dto.SignUpRequest;
import gift.user.model.dto.UpdatePasswordRequest;
import gift.user.model.dto.User;
import gift.user.resolver.LoginUser;
import gift.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok()
                .header("Authorization", token)
                .body("로그인 성공");
    }

    @PatchMapping("/password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
                                                 @LoginUser User loginUser) {
        userService.updatePassword(updatePasswordRequest, loginUser);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/email")
    public ResponseEntity<String> findEmail(@Valid @RequestParam Long id,
                                            @LoginUser User loginUser) {
        if (loginUser.id().equals(id)) {
            String password = userService.findEmail(id);
            return ResponseEntity.ok().body(password);
        }
        throw new ForbiddenException("비밀번호 찾기 실패: 로그인한 사용자의 이메일이 아닙니다");
    }

    @GetMapping("/admin/email")
    public ResponseEntity<String> findEmailForAdmin(@Valid @RequestParam Long id,
                                                    @LoginUser User loginUser) {
        userService.verifyAdminAccess(loginUser);
        String password = userService.findEmail(id);
        return ResponseEntity.ok().body(password);
    }
}
