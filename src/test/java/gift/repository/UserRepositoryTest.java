package gift.repository;

import gift.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User expected = new User("aaa@aaa",
                                 "aaa",
                                 "user");

        User actual = userRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
                () -> assertThat(actual.getRole()).isEqualTo(expected.getRole())
        );
    }

    @Test
    void findByEmail() {
        User expected = new User("aaa@aaa",
                                 "aaa",
                                 "user");
        userRepository.save(expected);

        User actual = userRepository.findByEmail("john.doe@example.com").orElse(null);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
                () -> assertThat(actual.getRole()).isEqualTo(expected.getRole())
        );
    }

    @Test
    void existsByEmail() {
        User expected = new User("aaa@aaa",
                                 "aaa",
                                 "user");
        userRepository.save(expected);

        boolean exists = userRepository.existsByEmail(expected.getEmail());
        assertThat(exists).isTrue();

        boolean notExist = userRepository.existsByEmail("bbb@bbb");
        assertThat(notExist).isFalse();
    }
}