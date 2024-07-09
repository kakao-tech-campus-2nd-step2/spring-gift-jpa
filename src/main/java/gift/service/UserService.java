package gift.service;

import gift.domain.User;
import gift.dto.UserLogin;
import gift.dto.UserSignUp.Request;
import gift.exception.UserErrorCode;
import gift.exception.UserException;
import gift.repository.UserJpaRepository;
import gift.repository.UserRepository;
import gift.util.JwtTokenProvider;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private JwtTokenProvider tokenProvider;
    public String signUp(Request request) {
        // 이메일 형식 검증
        if (!isValidEmail(request.getEmail())) {
            throw new UserException.BadRequest(UserErrorCode.INVALID_EMAIL_FORMAT);
        }
        // 이미 존재하는 이메일인지 검증
        if (userJpaRepository.existsByEmail(request.getEmail())) {
            throw new UserException.Conflict(UserErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String accessToken = tokenProvider.createToken(request.getEmail());
        User user = new User(request.getEmail(), request.getPassword(), accessToken);
        userJpaRepository.save(user);
        return accessToken;
    }

    public String login(UserLogin.Request request) {
        User user = userJpaRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
            .orElseThrow(() -> new UserException.Forbidden(UserErrorCode.WRONG_LOGIN));
        return user.getAccessToken();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
