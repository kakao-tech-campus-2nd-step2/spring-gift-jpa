package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(scripts = "/sql/insert_three_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("register user test")
    void registerUserTest() {
        // given
        User user = User.builder()
            .email("newuser@email.com")
            .password("password")
            .build();

        // when
        final User actual = userRepository.save(user);

        // then
        assertThat(userRepository.findAll()).hasSize(4);
        assertThat(actual.getId()).isNotNull().isEqualTo(4L);
        assertThat(actual.getEmail()).isEqualTo("newuser@email.com");
    }

    @Test
    @DisplayName("find by id test'")
    void findByIdTest() {
        // when
        final User actual = userRepository.findById(2L).get();
        User expected = User.builder()
            .id(2L)
            .email("user2@example.com")
            .build();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }

    @Test
    @DisplayName("find by not exist id test")
    void findByNotExistIdTest() {
        // when
        Optional<User> actual = userRepository.findById(10L);

        // then
        assertThat(actual).isNotPresent();
    }

    @Test
    @DisplayName("find by email test")
    void findByEmail() {
        // given
        String email = "user2@example.com";

        // when
        final User actual = userRepository.findByEmail(email).get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(2L);
        assertThat(actual.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("find by not exist email test")
    void findByNotExistEmailTest() {
        // given
        String email = "notuser@example.com";

        // when
        Optional<User> actual = userRepository.findByEmail(email);

        // then
        assertThat(actual).isNotPresent();
    }

    @Test
    @DisplayName("find by email and password test for login")
    void findByEmailAndPassword() {
        // given
        String email = "user2@example.com";
        String password = "password2";

        // when
        final User actual = userRepository.findByEmailAndPassword(email, password).get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(2L);
        assertThat(actual.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("wrong password test")
    void wrongPasswordTest() {
        // given
        String email = "user2@example.com";
        String password = "wrong_password";

        // when
        final Optional<User> actual = userRepository.findByEmailAndPassword(email, password);

        // then
        assertThat(actual).isNotPresent();
    }
}
