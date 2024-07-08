package gift.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import gift.exception.CustomException.DuplicateEmailException;
import gift.exception.CustomException.EmailNotFoundException;
import gift.exception.CustomException.PassWordMissMatchException;
import gift.model.user.UserForm;
import gift.service.JwtProvider;
import gift.service.UserService;

@RestController
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> handleLoginRequest(@Valid @RequestBody UserForm userForm,
        BindingResult result)
        throws MethodArgumentNotValidException {
        if (!userService.existsEmail(userForm.getEmail())) {
            result.rejectValue("email", "", "해당 이메일은 존재하지 않습니다.");
            throw new EmailNotFoundException(null, result);
        }
        if (!userService.isPassWordMatch(userForm)) {
            result.rejectValue("passWord", "", "비밀번호가 일치하지 않습니다.");
            throw new PassWordMissMatchException(null, result);
        }
        return ResponseEntity.ok(jwtProvider.generateToken(userService.findByEmail(userForm.getEmail())));
    }

    @PostMapping("/register")
    public ResponseEntity<?> handleSignUpRequest(@Valid @RequestBody UserForm userForm,
        BindingResult result) throws MethodArgumentNotValidException {
        if (userService.existsEmail(userForm.getEmail())) {
            result.rejectValue("email", "", "이미 존재하는 이메일입니다.");
            throw new DuplicateEmailException(null, result);
        }
        Long id = userService.insertUser(userForm);
        return ResponseEntity.ok(id);
    }

}
