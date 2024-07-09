package gift.JpaDataTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.user.User;
import gift.domain.user.UserService;
import gift.domain.user.dto.UserDTO;
import gift.domain.user.dto.UserInfo;
import gift.domain.user.repository.JpaUserRepository;
import gift.global.jwt.JwtProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserRepositoryTest {

    private final UserService userService;
    private final JpaUserRepository userRepository;
    private final JwtProvider jwtProvider;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public UserRepositoryTest(
        UserService userService,
        JpaUserRepository jpaUserRepository,
        JwtProvider jwtProvider
    ) {
        this.userService = userService;
        this.userRepository = jpaUserRepository;
        this.jwtProvider = jwtProvider;
    }

    @Test
    @Description("회원 가입")
    public void join() {
        // given
        User user = new User("minji@example.com", "password1");
        // when
        User savedUser = userRepository.saveAndFlush(user);
        // then
        assertAll(
            () -> assertThat(savedUser.getEmail()).isEqualTo("minji@example.com"),
            () -> assertThat(savedUser.getPassword()).isEqualTo("password1")
        );
    }

    @Test
    @Description("로그인")
    public void login() {
        // given
        User user = new User("minji@example.com", "password1");
        User savedUser = userRepository.saveAndFlush(user);

        UserDTO userDTO = new UserDTO("minji@example.com", "password1");
        // when
        String token = userService.login(userDTO);
        UserInfo userInfo = jwtProvider.getUserInfo(token);
        // then
        assertThat(userInfo.getId()).isEqualTo(savedUser.getId());
        assertThat(userInfo.getEmail()).isEqualTo(userDTO.getEmail());
    }

}
