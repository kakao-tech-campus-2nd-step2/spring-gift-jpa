package gift.service;

import gift.dto.LoginRequest;
import gift.dto.LoginResponse;
import gift.dto.UserRequest;
import gift.exception.user.UserAlreadyExistsException;
import gift.exception.user.UserNotFoundException;
import gift.jwt.JwtUtil;
import gift.model.User;
import gift.repository.UserDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public LoginResponse register(UserRequest request) {
        Optional<User> optionalUser = userDao.findByEmail(request.email());

        if (!optionalUser.isPresent()) {
            User user = new User(
                    request.email(),
                    request.password()
            );
            userDao.insert(user);
            LoginResponse response = new LoginResponse("");
            return response;
        }
        throw new UserAlreadyExistsException("해당 email의 계정이 이미 존재하는 계정입니다.");
    }

    public LoginResponse login(LoginRequest request) {
        User user = userDao.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다."));
        if (!user.matchPassword(request.password())) {
            throw new UserNotFoundException("비밀번호가 일치하지 않습니다.");
        }
        LoginResponse response = new LoginResponse(JwtUtil.createToken(user.getEmail()));
        return response;
    }
}
