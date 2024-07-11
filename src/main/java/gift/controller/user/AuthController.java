package gift.controller.user;

import gift.dto.user.TokenResponseDTO;
import gift.dto.user.UserRequestDTO;
import gift.exception.InvalidPasswordException;
import gift.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDTO> signUp(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        TokenResponseDTO tokenResponseDTO = userService.signUp(userRequestDTO);

        return ResponseEntity.ok(tokenResponseDTO);
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid UserRequestDTO userRequestDTO) throws InvalidPasswordException {
        TokenResponseDTO tokenResponseDTO = userService.login(userRequestDTO);

        return ResponseEntity.ok(tokenResponseDTO);
    }
}
