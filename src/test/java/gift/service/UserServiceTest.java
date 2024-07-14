package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.user.User;
import gift.model.user.UserRequest;
import gift.model.user.UserResponse;
import gift.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/truncate.sql")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입")
    void register() {
        UserRequest userRequest = new UserRequest("yso8296", "yso8296@gmail.com");

        UserResponse actual = userService.register(userRequest);

        assertAll(
            () -> assertThat(actual.id()).isNotNull(),
            () -> assertThat(actual.password()).isEqualTo("yso8296"),
            () -> assertThat(actual.email()).isEqualTo("yso8296@gmail.com")
        );
    }
}
