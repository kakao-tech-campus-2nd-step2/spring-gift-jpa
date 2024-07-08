package gift.service.auth;

import gift.dto.LoginRequest;
import gift.dto.RegisterRequest;
import gift.exception.DuplicatedEmailException;
import gift.exception.InvalidLoginInfoException;
import gift.model.MemberRole;
import gift.service.MemberService;
import gift.reflection.AuthTestReflectionComponent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthTestReflectionComponent authTestReflectionComponent;

    @Test
    @DisplayName("회원가입 시도하기 - 성공")
    void registerSuccess() {
        var registerRequest = new RegisterRequest("테스트", "test@naver.com", "testPassword", "MEMBER");
        var auth = authService.register(registerRequest);
        var id = authTestReflectionComponent.getMemberIdWithToken(auth.token());
        var role = authTestReflectionComponent.getMemberRoleWithToken(auth.token());

        Assertions.assertThat(role).isEqualTo(MemberRole.MEMBER);

        memberService.deleteMember(id);
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입 시도하기 - 실패")
    void registerFailWithDuplicatedEmail() {
        var registerRequest = new RegisterRequest("테스트", "test@naver.com", "testPassword", "MEMBER");
        var auth = authService.register(registerRequest);
        var id = authTestReflectionComponent.getMemberIdWithToken(auth.token());

        Assertions.assertThatThrownBy(() -> authService.register(registerRequest)).isInstanceOf(DuplicatedEmailException.class);

        memberService.deleteMember(id);
    }

    @Test
    @DisplayName("로그인 실행하기 - 성공")
    void loginSuccess() {
        var registerRequest = new RegisterRequest("테스트", "test@naver.com", "testPassword", "MEMBER");
        authService.register(registerRequest);

        var loginRequest = new LoginRequest("test@naver.com", "testPassword");
        var auth = authService.login(loginRequest);
        var id = authTestReflectionComponent.getMemberIdWithToken(auth.token());
        var role = authTestReflectionComponent.getMemberRoleWithToken(auth.token());

        Assertions.assertThat(role).isEqualTo(MemberRole.MEMBER);

        memberService.deleteMember(id);
    }

    @Test
    @DisplayName("로그인 실행하기 - 실패")
    void loginFailWithWrongPassword() {
        var registerRequest = new RegisterRequest("테스트", "test@naver.com", "testPasswords", "MEMBER");
        var auth = authService.register(registerRequest);
        var loginRequest = new LoginRequest("test@naver.com", "testPassword");

        Assertions.assertThatThrownBy(() -> authService.login(loginRequest)).isInstanceOf(InvalidLoginInfoException.class);

        var id = authTestReflectionComponent.getMemberIdWithToken(auth.token());

        memberService.deleteMember(id);
    }
}
