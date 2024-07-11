package gift.permission.service;

import gift.global.component.TokenComponent;
import gift.global.dto.TokenDto;
import gift.user.dto.UserRequestDto;
import gift.user.dto.UserResponseDto;
import gift.user.entity.User;
import gift.permission.repository.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// UserController로부터 입력을 받아서 엔터티를 사용해서 비즈니스 로직 수행
@Service
public class PermissionService {

    // ID를 배정하기 위한 변수. 아직은 서버가 한 대라서 이런 방식을 사용했습니다.
    private long userId = 2;
    private final PermissionDao permissionDao;
    private final TokenComponent tokenComponent;

    @Autowired
    public PermissionService(PermissionDao permissionDao, TokenComponent tokenComponent) {
        this.permissionDao = permissionDao;
        this.tokenComponent = tokenComponent;
    }

    // 회원가입 비즈니스 로직 처리
    public void register(UserRequestDto userRequestDto) {
        permissionDao.insertUser(
            new User(getUserId(), userRequestDto.email(), userRequestDto.password()));
    }

    // 로그인 비즈니스 로직 처리
    public TokenDto login(UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = permissionDao.selectUser(userRequestDto.email());

        // 비밀번호 검증
        verifyPassword(userRequestDto.password(), userResponseDto.password());

        // 비밀번호 검증이 완료되면 토큰 발급
        return tokenComponent.getToken(userResponseDto.userId(), userResponseDto.email(),
            userResponseDto.password());
    }

    // id를 배정하기 위해, id를 갖다 쓰고, 1 더하는 식으로 사용
    private long getUserId() {
        return userId++;
    }

    // 입력으로 들어온 비밀번호를 검증하는 로직
    private void verifyPassword(String inputPassword, String realPassword) {
        // 요구 사항: 비밀번호가 옳지 않으면 FORBIDDEN 반환.
        if (!inputPassword.equals(realPassword)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 맞지 않습니다.");
        }
    }
}