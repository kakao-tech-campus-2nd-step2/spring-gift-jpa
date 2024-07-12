package gift.permission.service;

import gift.global.component.TokenComponent;
import gift.global.dto.TokenDto;
import gift.user.dto.UserRequestDto;
import gift.permission.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// UserController로부터 입력을 받아서 엔터티를 사용해서 비즈니스 로직 수행
@Service
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
    }

    // 로그인 비즈니스 로직 처리
    public TokenDto login(UserRequestDto userRequestDto) {

        // 비밀번호 검증

        // 비밀번호 검증이 완료되면 토큰 발급

        return new TokenDto(null, 0);
    }

    // 입력으로 들어온 비밀번호를 검증하는 로직
    private void verifyPassword(String inputPassword, String realPassword) {
        // 요구 사항: 비밀번호가 옳지 않으면 FORBIDDEN 반환.
        if (!inputPassword.equals(realPassword)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 맞지 않습니다.");
        }
    }
}