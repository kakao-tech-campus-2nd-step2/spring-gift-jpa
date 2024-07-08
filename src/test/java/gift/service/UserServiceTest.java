package gift.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import gift.dto.user.UserLoginRequest;
import gift.dto.user.UserRegisterRequest;
import gift.dto.user.UserResponse;
import gift.exception.user.UserAlreadyExistException;
import gift.exception.user.UserNotFoundException;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS users CASCADE");
        jdbcTemplate.execute("CREATE TABLE users ("
            + "id LONG AUTO_INCREMENT PRIMARY KEY,"
            + " email VARCHAR(255),"
            + " password VARCHAR(255))"
        );
    }

    @Test
    @DisplayName("register user test")
    void registerUserTest() {
        //given
        UserRegisterRequest user1Request = new UserRegisterRequest("user1@email.com", "1q2w3e4r!");

        //when
        UserResponse user1Response = userService.registerUser(user1Request);
        UserResponse expected = new UserResponse(1L, "user1@email.com", Base64.getEncoder()
            .encodeToString(("user1@email.com:1q2w3e4r!")
                .getBytes()));

        //then
        assertThat(user1Response).isEqualTo(expected);
    }

    @Test
    @DisplayName("Already Exist user registration test")
    void alreadyExistUserRegistrationTest() {
        //given
        UserRegisterRequest user1Request = new UserRegisterRequest("user1@email.com", "1q2w3e4r!");
        UserRegisterRequest user2Request = new UserRegisterRequest("user1@email.com", "1234");

        //when
        userService.registerUser(user1Request);

        //when&then
        assertThatThrownBy(() -> userService.registerUser(user2Request))
            .isInstanceOf(UserAlreadyExistException.class);
    }

    @Test
    @DisplayName("user login test")
    void userLoginTest() {
        //given
        UserRegisterRequest registerRequest = new UserRegisterRequest("user1@email.com", "1q2w3e4r!");
        UserLoginRequest loginRequest = new UserLoginRequest("user1@email.com", "1q2w3e4r!");
        String token = userService.registerUser(registerRequest).token();

        //when
        UserResponse response = userService.loginUser(loginRequest);

        //then
        assertThat(response.token()).isEqualTo(token);
    }

    @Test
    @DisplayName("unknown user login test")
    void unknownUserLoginTest() {
        //given
        UserLoginRequest user1Request = new UserLoginRequest("user1@email.com", "1q2w3e4r!");

        //when & then
        assertThatThrownBy(() -> userService.loginUser(user1Request))
            .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("wrong password login test")
    void wrongPasswordLoginTest() {
        //given
        UserRegisterRequest user1Request = new UserRegisterRequest("user1@email.com", "1q2w3e4r!");
        UserLoginRequest user2Request = new UserLoginRequest("user1@email.com", "1234");
        userService.registerUser(user1Request);

        //when & then
        assertThatThrownBy(() -> userService.loginUser(user2Request))
            .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("getUserIdByToken test")
    void getUserIdByTokenTest() {
        //given
        UserRegisterRequest registerRequest = new UserRegisterRequest("user1@example.com", "password1");
        userService.registerUser(registerRequest);

        UserLoginRequest loginRequest = new UserLoginRequest("user1@example.com", "password1");

        //when
        UserResponse loginUserResponse = userService.loginUser(loginRequest);
        String token = loginUserResponse.token();
        Long userId = loginUserResponse.id();

        //then
        assertThat(userId).isEqualTo(userService.getUserIdByToken(token));
    }
}
