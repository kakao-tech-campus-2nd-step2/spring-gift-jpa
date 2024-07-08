package gift.service;

import gift.dto.LoginRequest;
import gift.dto.RegisterRequest;
import gift.exception.NotFoundElementException;
import gift.service.auth.AuthService;
import gift.reflection.AuthTestReflectionComponent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthTestReflectionComponent authTestReflectionComponent;

    @Test
    @DisplayName("회원 탈퇴하기 - 성공")
    void deleteMemberSuccess() {
        var registerRequest = new RegisterRequest("테스트", "test@naver.com", "testPassword", "MEMBER");
        var loginRequest = new LoginRequest("test@naver.com", "testPassword");
        var auth = authService.register(registerRequest);
        var id = authTestReflectionComponent.getMemberIdWithToken(auth.token());
        var loginAuth = authService.login(loginRequest);

        Assertions.assertThat(auth.token()).isEqualTo(loginAuth.token());

        memberService.deleteMember(id);

        Assertions.assertThatThrownBy(() -> authService.login(loginRequest)).isInstanceOf(NotFoundElementException.class);
    }
}
