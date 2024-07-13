package gift.permission.service;

import gift.global.component.TokenComponent;
import gift.global.dto.TokenDto;
import gift.permission.repository.PermissionRepository;
import gift.user.dto.UserRequestDto;
import gift.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

// UserController로부터 입력을 받아서 엔터티를 사용해서 비즈니스 로직 수행
@Service
@Transactional
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final TokenComponent tokenComponent;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository,
        TokenComponent tokenComponent) {
        this.permissionRepository = permissionRepository;
        this.tokenComponent = tokenComponent;
    }

    // 회원가입 비즈니스 로직 처리
    public void register(UserRequestDto userRequestDto) {
        verifyEmailAlreadyExistence(userRequestDto.email());
        User user = new User(userRequestDto.email(), userRequestDto.password());
        permissionRepository.save(user);
    }

    // 로그인 비즈니스 로직 처리
    public TokenDto login(UserRequestDto userRequestDto) {
        String email = userRequestDto.email();
        String password = userRequestDto.password();

        // 이메일 불러오기
        User actualUser = permissionRepository.findByEmail(email).get();

        // 비밀번호 검증
        verifyPassword(password, actualUser.getPassword());

        // 비밀번호 검증이 완료되면 토큰 발급
        return tokenComponent.getToken(actualUser.getUserId(), email, password);
    }

    // 이미 가입된 이메일인지 검증
    private void verifyEmailAlreadyExistence(String email) {
        if (permissionRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }

    // 가입이 안 된 이메일인지 검증
    private void verifyEmailExistence(String email) {
        if (!permissionRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("가입되지 않은 이메일입니다.");
        }
    }

    // 입력으로 들어온 비밀번호를 검증하는 로직
    private void verifyPassword(String inputPassword, String realPassword) {
        // 요구 사항: 비밀번호가 옳지 않으면 FORBIDDEN 반환.
        if (!inputPassword.equals(realPassword)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 맞지 않습니다.");
        }
    }
}