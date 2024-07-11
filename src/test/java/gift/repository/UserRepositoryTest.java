package gift.repository;

import gift.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = new User("test1@example.com", "password1");
        User user2 = new User("test2@example.com", "password2");
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void saveTest() {
        User user = new User("abc@email.com", "1234");
        User actual = userRepository.save(user);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(user.getEmail())
        );
    }

    @Test
    void findByEmailTest() {
        // Given
        String emailToFind = "test1@example.com";

        // When
        Optional<User> foundUser = userRepository.findByEmail(emailToFind);

        // Then
        assertAll(
                () -> assertThat(foundUser).isPresent(),
                () -> assertThat(foundUser.get().getEmail()).isEqualTo(emailToFind)
        );
    }

}