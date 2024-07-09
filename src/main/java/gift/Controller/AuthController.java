package gift.Controller;

import gift.DAO.UserDAO;
import gift.DTO.AuthRequestDTO;
import gift.DTO.AuthResponseDTO;
import gift.Entity.UserEntity;
import gift.Service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/login/token")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
        UserEntity user = (UserEntity) userDao.findByEmail(authRequest.getEmail());

        if (isInvalidUser(user, authRequest)) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "잘못된 인증입니다.");
        }

        String token = tokenService.generateToken(user.getEmail());
        AuthResponseDTO response = new AuthResponseDTO(token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/protected")
    public ResponseEntity<?> getProtectedPage(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        String email = tokenService.getEmailFromToken(token);

        if (email == null) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "인증 정보가 없습니다.");
        }

        return ResponseEntity.ok("인증되었습니다, " + email);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO authRequest) {

        if (isEmailAlreadyExist(authRequest.getEmail())) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "이미 존재하는 email입니다.");
        }

        UserEntity newUser = createUser(authRequest);
        userDao.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    private boolean isInvalidUser(UserEntity user, AuthRequestDTO authRequest) {
        return user == null || !user.getPassword().equals(authRequest.getPassword());
    }

    private boolean isEmailAlreadyExist(String email) {
        return userDao.findByEmail(email) != null;
    }

    private UserEntity createUser(AuthRequestDTO authRequest) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(authRequest.getEmail());
        newUser.setPassword(authRequest.getPassword());
        return newUser;
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String detail) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setDetail(detail);
        return ResponseEntity.status(status).body(problemDetail);
    }
}
