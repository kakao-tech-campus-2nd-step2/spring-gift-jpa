package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void testSave() {
        User user = new User(1L, "kbm", "kbm@kbm", "mbk", "user");
        User savedUser = userRepository.save(user);
        assertAll(
            () -> assertThat(savedUser.getId()).isNotNull(),
            () -> assertThat(savedUser.getName()).isEqualTo(user.getName()),
            () -> assertThat(savedUser.getEmail()).isEqualTo(user.getEmail()),
            () -> assertThat(savedUser.getPassword()).isEqualTo(user.getPassword()),
            () -> assertThat(savedUser.getRole()).isEqualTo(user.getRole())
        );
    }

    @Test
    void testFindByEmail() {
        User user = new User(1L, "kbm", "kbm@kbm", "mbk", "user");
        User savedUser = userRepository.save(user);
        assertAll(
            () -> assertThat(savedUser).isNotNull(),
            () -> assertThat(savedUser.getId()).isEqualTo(user.getId())
        );
    }

}