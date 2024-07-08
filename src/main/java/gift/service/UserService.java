package gift.service;

import gift.auth.jwt.JwtProvider;
import gift.controller.user.dto.UserRequest.Login;
import gift.controller.user.dto.UserRequest.Register;
import gift.model.user.User;
import gift.model.user.UserDao;
import gift.validate.InvalidAuthRequestException;
import gift.validate.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;
    private final JwtProvider jwtProvider;

    public UserService(UserDao userDao, JwtProvider jwtProvider) {
        this.userDao = userDao;
        this.jwtProvider = jwtProvider;
    }

    public void register(Register request) {
        userDao.findByEmail(request.email()).ifPresent(user -> {
            throw new InvalidAuthRequestException("User already exists.");
        });
        userDao.insert(request.toEntity());
    }

    public String login(Login request) {
        var user = userDao.findByEmail(request.email())
            .orElseThrow(() -> new NotFoundException("User not found."));

        if (!user.verifyPassword(request.password())) {
            throw new InvalidAuthRequestException("Password is incorrect.");
        }
        return jwtProvider.createToken(user.getId(), user.getRole());

    }

    public User getUser(String userId) {
        return userDao.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));
    }
}
