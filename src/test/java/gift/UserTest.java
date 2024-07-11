package gift;

import gift.domain.model.User;
import gift.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserTest() {
        // given
        User user = new User("test@example.com", "password123");

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getPassword()).isEqualTo("password123");
    }

    @Test
    public void findByIdTest() {
        // given
        User user = new User("test@example.com", "password123");
        User savedUser = userRepository.save(user);

        // when
        User found = userRepository.findById(savedUser.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
        assertThat(found.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void findByEmailTest() {
        // given
        User user = new User("test@example.com", "password123");
        userRepository.save(user);

        // when
        User found = userRepository.findByEmail("test@example.com").orElse(null);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
        assertThat(found.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void updateUserTest() {
        // given
        User user = new User("test@example.com", "password123");
        User savedUser = userRepository.save(user);

        // when
        savedUser.setEmail("updated@example.com");
        savedUser.setPassword("newpassword123");
        User updatedUser = userRepository.save(savedUser);

        // then
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");
        assertThat(updatedUser.getPassword()).isEqualTo("newpassword123");
    }
}