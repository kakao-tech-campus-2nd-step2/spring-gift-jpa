package gift.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import gift.auth.jwt.JwtProvider;
import gift.auth.jwt.Token;
import gift.domain.user.dao.UserDao;
import gift.domain.user.dao.UserJpaRepository;
import gift.domain.user.dto.UserDto;
import gift.domain.user.dto.UserLoginDto;
import gift.domain.user.entity.Role;
import gift.domain.user.entity.User;
import gift.exception.InvalidUserInfoException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureMockMvc
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserJpaRepository userJpaRepository;
    
    @MockBean
    private JwtProvider jwtProvider;
    

    @Test
    @DisplayName("회원 가입 서비스 테스트")
    void signUp_success() {
        // given
        UserDto userDto = new UserDto(null, "testUser", "test@test.com", "test123", null);

        User user = userDto.toUser();
        user.setId(1L);
        given(userJpaRepository.save(any(User.class))).willReturn(user);

        Token expectedToken = new Token("token");
        given(jwtProvider.generateToken(any(User.class))).willReturn(expectedToken);

        // when
        Token actualToken = userService.signUp(userDto);

        // then
        assertThat(actualToken).isEqualTo(expectedToken);
    }

    @Test
    @DisplayName("로그인 서비스 테스트")
    void login_success() {
        // given
        UserLoginDto loginDto = new UserLoginDto("test@test.com", "test123");

        User user = new User(1L, "testUser", "test@test.com", "test123", Role.USER);
        given(userJpaRepository.findByEmail(eq("test@test.com"))).willReturn(Optional.of(user));

        Token expectedToken = new Token("token");
        given(jwtProvider.generateToken(any(User.class))).willReturn(expectedToken);

        // when
        Token actualToken = userService.login(loginDto);

        // then
        assertThat(actualToken).isEqualTo(expectedToken);
    }

    @Test
    @DisplayName("로그인 서비스 테스트 - 존재하지 않는 이메일")
    void login_fail_email_error() {
        // given
        UserLoginDto loginDto = new UserLoginDto("test@test.com", "test123");

        given(userJpaRepository.findByEmail(eq("test@test.com"))).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.login(loginDto))
            .isInstanceOf(InvalidUserInfoException.class)
            .hasMessage("error.invalid.userinfo.email");
    }

    @Test
    @DisplayName("로그인 서비스 테스트 - 틀린 비밀번호")
    void login_fail_password_error() {
        // given
        UserLoginDto loginDto = new UserLoginDto("test@test.com", "test123");

        User user = mock(User.class);
        given(userJpaRepository.findByEmail(eq("test@test.com"))).willReturn(Optional.of(user));
        given(user.checkPassword(eq("test123"))).willReturn(false);

        // when & then
        assertThatThrownBy(() -> userService.login(loginDto))
            .isInstanceOf(InvalidUserInfoException.class)
            .hasMessage("error.invalid.userinfo.password");
    }
}