package gift.controller.signup;

import gift.DTO.SignupRequest;
import gift.DTO.SignupResponse;
import gift.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<SignupResponse> signup(
        @RequestBody SignupRequest signupRequest,
        Model model) {
        SignupResponse signupResponse = userService.registerUser(signupRequest);
        return new ResponseEntity<>(signupResponse, HttpStatus.CREATED);
    }
}