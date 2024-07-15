package gift.domain.user;

import gift.domain.user.dto.UserDTO;
import gift.global.response.ResponseMaker;
import gift.global.response.SimpleResultResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원 가입
     */
    @PostMapping
    public ResponseEntity<SimpleResultResponseDto> join(@Valid @ModelAttribute UserDTO userDTO) {
        userService.join(userDTO);

        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "회원 가입에 성공했습니다");
    }

    /**
     * 회원 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<SimpleResultResponseDto> login(@Valid @RequestBody UserDTO userDTO) {
        String jwt = userService.login(userDTO);

        return ResponseMaker.createSimpleResponseWithJwtOnHeader(HttpStatus.OK, "로그인에 성공했습니다", jwt);
    }

}
