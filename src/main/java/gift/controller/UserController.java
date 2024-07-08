package gift.controller;

import gift.common.exception.UserAlreadyExistsException;
import gift.common.exception.UserNotFoundException;
import gift.common.util.PasswordProvider;
import gift.controller.dto.request.UserSignUpRequest;
import gift.controller.dto.response.UserSignInResponse;
import gift.model.JwtProvider;
import gift.model.User;
import gift.model.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User관련 API")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserController(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Operation(summary = "회원 가입", description = "회원 가입을 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 사용자"),
            @ApiResponse(responseCode = "404", description = "회원가입이 올바르게 이루어지지 않음")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<UserSignInResponse> signUp(@RequestBody UserSignUpRequest userSignupRequest) {
        User user = userSignupRequest.toModel();

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        userRepository.save(user);
        User savedUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(UserNotFoundException::new);
        if (PasswordProvider.match(userSignupRequest.username(), userSignupRequest.password(),
                savedUser.getPassword())) {
            throw new UserNotFoundException();
        }

        String token = jwtProvider.generateToken(savedUser);

        return ResponseEntity
                .created(URI.create("/api/users/" + savedUser.getId().toString()))
                .body(new UserSignInResponse(token));
    }

    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<UserSignInResponse> signIn(@RequestBody UserSignUpRequest userSignupRequest) {
        User savedUser = userRepository.findByUsername(userSignupRequest.username())
                .orElseThrow(UserNotFoundException::new);
        if (PasswordProvider.match(userSignupRequest.username(), userSignupRequest.password(),
                savedUser.getPassword())) {
            throw new UserNotFoundException();
        }

        String token = jwtProvider.generateToken(savedUser);

        return ResponseEntity.ok(new UserSignInResponse(token));
    }
}
