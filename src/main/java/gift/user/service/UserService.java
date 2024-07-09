package gift.user.service;

import gift.user.exception.ForbiddenException;
import gift.user.jwt.JwtService;
import gift.user.model.UserRepository;
import gift.user.model.dto.LoginRequest;
import gift.user.model.dto.SignUpRequest;
import gift.user.model.dto.UpdatePasswordRequest;
import gift.user.model.dto.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.SHA256Util;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        String salt = SHA256Util.getSalt();
        String hashedPassword = SHA256Util.encodePassword(signUpRequest.getPassword(), salt);
        signUpRequest.setPassword(hashedPassword);
        User user = new User(signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getRole(), salt);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailAndIsActiveTrue(loginRequest.email())
                .orElseThrow(() -> new EntityNotFoundException("User"));
        if (!isPasswordCorrect(loginRequest.password(), user)) {
            throw new ForbiddenException("로그인 실패: 비밀번호 불일치");
        }
        return jwtService.createToken(user.getId());
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest, User loginUser) {
        if (!isPasswordCorrect(updatePasswordRequest.oldPassword(), loginUser)) {
            throw new ForbiddenException("비밀번호 변경 실패: 기존 비밀번호 불일치");
        }

        String newHashedPassword = SHA256Util.encodePassword(updatePasswordRequest.newPassword(),
                loginUser.getSalt());

        loginUser.setPassword(newHashedPassword);
        userRepository.save(loginUser);
    }

    @Transactional(readOnly = true)
    public String findEmail(Long id) {
        User user = userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("User"));
        return user.getEmail();
    }

    private boolean isPasswordCorrect(String inputPassword, User user) {
        String hashedInputPassword = SHA256Util.encodePassword(inputPassword, user.getSalt());
        return hashedInputPassword.equals(user.getPassword());
    }


    public void verifyAdminAccess(User user) {
        if (!user.getRole().equals("ADMIN")) {
            throw new ForbiddenException("해당 요청에 대한 관리자 권한이 없습니다.");
        }
    }
}