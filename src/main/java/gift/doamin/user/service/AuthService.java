package gift.doamin.user.service;

import gift.doamin.user.entity.User;
import gift.doamin.user.entity.UserRole;
import gift.doamin.user.dto.LoginForm;
import gift.doamin.user.dto.SignUpForm;
import gift.doamin.user.repository.UserRepository;
import gift.global.JwtProvider;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
        JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public void signUp(SignUpForm signUpForm) {
        String email = signUpForm.getEmail();
        if (userRepository.findByEmail(email) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 이메일은 사용할 수 없습니다");
        }

        UserRole role = signUpForm.getRole();
        if (UserRole.ADMIN == role) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ADMIN으로 가입하실 수 없습니다.");
        }

        String password = passwordEncoder.encode(signUpForm.getPassword());
        String name = signUpForm.getName();

        User user = new User(email, password, name, role);
        userRepository.save(user);
    }

    public String login(LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        User user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "일치하는 계정이 없습니다.");
        }

        return jwtProvider.generateToken(user.getId(), user.getRole());
    }
}
