package gift.JpaDataTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.user.User;
import gift.domain.user.UserService;
import gift.domain.user.dto.UserDTO;
import gift.domain.user.dto.UserInfo;
import gift.domain.user.JpaUserRepository;
import gift.global.jwt.JwtProvider;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    UserService userService;
    @Autowired
    JpaUserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        User user = new User("minji@example.com", "password1");
        this.user = user;
        UserDTO userDTO = new UserDTO("minji@example.com", "password1");
        this.userDTO = userDTO;
    }
    @Test
    @Description("회원 가입")
    public void join() {
        // when
        User savedUser = userRepository.saveAndFlush(user);
        // then
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    @Description("로그인")
    public void login() {
        // given
        User savedUser = userRepository.saveAndFlush(user);
        // when
        String token = userService.login(userDTO);
        UserInfo userInfo = jwtProvider.getUserInfo(token);
        // then
        assertThat(userInfo.getId()).isEqualTo(savedUser.getId());
        assertThat(userInfo.getEmail()).isEqualTo(userDTO.getEmail());
    }

}
