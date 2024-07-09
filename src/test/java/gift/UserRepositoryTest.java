package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.User;
import gift.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User expected = new User();
        expected.setEmail("test@example.com");
        expected.setPassword("password");
        User actual = userRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        );
    }

    @Test
    void findByEmail() {
        String expected = "test@example.com";
        User user = new User();
        user.setEmail(expected);
        user.setPassword("password");
        userRepository.save(user);
        User actual = userRepository.findByEmail(expected);
        assertThat(actual.getEmail()).isEqualTo(expected);
    }
}