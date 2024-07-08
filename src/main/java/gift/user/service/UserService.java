package gift.user.service;

import gift.user.exception.ForbiddenException;
import gift.user.jwt.JwtService;
import gift.user.model.UserRepository;
import gift.user.model.dto.LoginRequest;
import gift.user.model.dto.SignUpRequest;
import gift.user.model.dto.UpdatePasswordRequest;
import gift.user.model.dto.User;
import org.springframework.stereotype.Service;
import security.SHA256Util;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public void signUp(SignUpRequest signUpRequest) {
        String salt = SHA256Util.getSalt();
        String hashedPassword = SHA256Util.encodePassword(signUpRequest.getPassword(), salt);
        signUpRequest.setPassword(hashedPassword);
        if (userRepository.signUpUser(signUpRequest, salt) <= 0) {
            throw new IllegalArgumentException("회원가입 실패");
        }
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null) {
            if (isPasswordCorrect(loginRequest.getPassword(), user)) {
                return jwtService.createToken(user.id());
            }
        }
        throw new ForbiddenException("로그인 실패");
    }

    private boolean isPasswordCorrect(String inputPassword, User user) {
        String hashedInputPassword = SHA256Util.encodePassword(inputPassword, user.salt());
        return hashedInputPassword.equals(user.password());
    }

    public void updatePassword(UpdatePasswordRequest updatePasswordRequest, User loginUser) {
        if (isPasswordCorrect(updatePasswordRequest.getOldPassword(), loginUser)) {
            String newHashedPassword = SHA256Util.encodePassword(updatePasswordRequest.getNewPassword(),
                    loginUser.salt());
            if (userRepository.updatePassword(loginUser.id(), newHashedPassword) <= 0) {
                throw new IllegalArgumentException("비밀번호 변경 실패");
            }
        }
        throw new ForbiddenException("비밀번호 변경 실패: 기존 비밀번호 불일치");
    }

    public String findEmail(Long id) {
        String email = userRepository.findEmail(id);
        if (email != null) {
            return email;
        }
        throw new IllegalArgumentException("이메일 찾기 실패");
    }

    public void verifyAdminAccess(User user) {
        if (!user.role().equals("ADMIN")) {
            throw new ForbiddenException("해당 요청에 대한 관리자 권한이 없습니다.");
        }
    }
}