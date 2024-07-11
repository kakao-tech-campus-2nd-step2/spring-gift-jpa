package gift.controller;

import gift.dao.UserDAO;
import gift.dto.AuthRequest;
import gift.dto.AuthResponse;
import gift.entity.User;
import gift.exception.EmailAlreadyExistsException;
import gift.exception.UserAuthException;
import gift.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final TokenService tokenService;
    private final UserDAO userDao;

    @Autowired
    public AuthController(TokenService tokenService, UserDAO userDao) {
        this.tokenService = tokenService;
        this.userDao = userDao;
    }

    @PostMapping("/users/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        User user = userDao.findByEmail(authRequest.getEmail());

        if (user == null || !user.samePassword(authRequest.getPassword())) {
            throw new UserAuthException("잘못된 로그인입니다.");
        }

        String token = tokenService.generateToken(user.getEmail());
        AuthResponse response = new AuthResponse(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        if (userDao.findByEmail(authRequest.getEmail()) != null) {
            throw new EmailAlreadyExistsException("이미 존재하는 email입니다.");
        }

        User newUser = new User(authRequest.getEmail(), authRequest.getPassword());
        userDao.save(newUser);

        String token = tokenService.generateToken(newUser.getEmail());
        AuthResponse response = new AuthResponse(token);

        return ResponseEntity.ok(response);
    }
}
