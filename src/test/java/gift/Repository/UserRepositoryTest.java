package gift.Repository;

import gift.Entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        UserEntity user = new UserEntity("test@example.com", "password123");

        UserEntity savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getPassword()).isEqualTo("password123");
    }

    @Test
    public void testFindByEmail() {
        UserEntity user = new UserEntity("unique@example.com", "password456");
        userRepository.save(user);

        Optional<UserEntity> foundUser = userRepository.findByEmail("unique@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("unique@example.com");
        assertThat(foundUser.get().getPassword()).isEqualTo("password456");
    }
}
