package gift.controller.restcontroller;

import gift.controller.dto.request.SignUpRequest;
import gift.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "SignUp", description = "회원가입 API")
@RestController
@RequestMapping("/api/v1")
public class SignUpRestController {
    private final SignUpService signUpService;

    public SignUpRestController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "회원가입을 시도합니다.")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        signUpService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
