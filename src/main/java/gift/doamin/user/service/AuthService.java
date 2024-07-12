package gift.doamin.user.service;

import gift.doamin.user.dto.LoginForm;
import gift.doamin.user.dto.SignUpForm;
import gift.doamin.user.entity.User;
import gift.doamin.user.entity.UserRole;
import gift.doamin.user.exception.InvalidSignUpFormException;
import gift.doamin.user.exception.UserNotFoundException;
import gift.doamin.user.repository.JpaUserRepository;
import gift.global.JwtProvider;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(JpaUserRepository userRepository, PasswordEncoder passwordEncoder,
        JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public void signUp(SignUpForm signUpForm) {
        String email = signUpForm.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new InvalidSignUpFormException("중복된 이메일은 사용할 수 없습니다");
        }

        UserRole role = signUpForm.getRole();
        if (UserRole.ADMIN == role) {
            throw new InvalidSignUpFormException("ADMIN으로 가입하실 수 없습니다.");
        }

        String password = passwordEncoder.encode(signUpForm.getPassword());
        String name = signUpForm.getName();

        User user = new User(email, password, name, role);
        userRepository.save(user);
    }

    public String login(LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new UserNotFoundException();
        }

        return jwtProvider.generateToken(user.get().getId(), user.get().getRole());
    }
}
