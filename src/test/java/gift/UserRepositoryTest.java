package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.user.User;
import gift.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void save(){
        User user = new User("admin@email.com","1234");
        User actual = userRepository.save(user);
        assertThat(actual.getEmail()).isEqualTo("admin@email.com");
        assertThat(actual.getPassword()).isEqualTo("1234");
    }

    @Test
    void existsByEmailAndPassword() {
        User user = new User("admin@email.com","1234");
        userRepository.save(user);
        Boolean actual = userRepository.existsByEmail(user.getEmail());
        assertThat(actual).isEqualTo(true);
        actual = userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(actual).isEqualTo(true);
    }
}
