package gift.authentication.restapi;

import gift.authentication.restapi.dto.request.LoginRequest;
import gift.authentication.restapi.dto.request.SignUpRequest;
import gift.authentication.restapi.dto.response.LoginResponse;
import gift.core.domain.authentication.AuthenticationService;
import gift.core.domain.authentication.Token;
import gift.core.domain.user.User;
import gift.core.domain.user.UserAccount;
import gift.core.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(
            AuthenticationService authenticationService,
            UserService userService
    ) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/api/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Token token = authenticationService.authenticate(request.email(), request.password());
        return LoginResponse.of(token);
    }

    @PostMapping("/api/auth/signup")
    public void signUp(@RequestBody SignUpRequest request) {
        userService.registerUser(userOf(request));
    }

    private User userOf(SignUpRequest request) {
        return new User(
                0L,
                request.name(),
                new UserAccount(
                        request.email(),
                        request.password()
                )
        );
    }
}
