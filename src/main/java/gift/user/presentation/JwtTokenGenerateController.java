package gift.user.presentation;

import gift.user.application.UserManageService;
import gift.user.application.UserManageService.CreateUserRequestDTO;
import gift.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtTokenGenerateController {

    @Autowired
    private UserManageService userManageService;

    @PostMapping("/token")
    public ResponseEntity<CommonResponse<String>> generateToken(
        @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        String token = userManageService.registerUser(createUserRequestDTO.getName(), createUserRequestDTO.getEmail());
        return ResponseEntity.ok(new CommonResponse<>(token, "토큰이 정상적으로 발급되었습니다", true));
    }


}
