package gift.repository;

import gift.entity.User;
import gift.entity.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        // given
        UserDTO actual = new UserDTO("test@naver.com", "123");

        // when
        User expect = userRepository.save(new User(actual));

        // then
        Assertions.assertAll(
                () -> assertThat(expect.getId()).isNotNull(),
                () -> assertThat(expect.getEmail()).isEqualTo(actual.getEmail()),
                () -> assertThat(expect.getPassword()).isEqualTo(actual.getPassword())
        );
    }
}
