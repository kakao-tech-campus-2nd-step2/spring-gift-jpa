package gift.repository;

import gift.config.SpringConfig;
import gift.model.User;
import gift.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(SpringConfig.class)
public class JpaUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        // given
        UserDTO actual = new UserDTO("test@naver.com", "123");

        // when
        User expect = userRepository.save(actual);

        // then
        Assertions.assertAll(
                () -> assertThat(expect.getId()).isNotNull(),
                () -> assertThat(expect.getEmail()).isEqualTo(actual.getEmail()),
                () -> assertThat(expect.getPassword()).isEqualTo(actual.getPassword())
        );
    }
}
