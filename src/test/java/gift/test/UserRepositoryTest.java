package gift.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import gift.model.User;
import gift.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
    	User expected = new User("test@test.com", "pw");
        User actual = userRepository.save(expected);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }

    @Test
    void findByEmail() {
        String expectedEmail = "test@test.com";
        User user = new User(expectedEmail, "pw");
        userRepository.save(user);
        Optional<User> actual = userRepository.findByEmail(expectedEmail);
        assertThat(actual).isPresent();
        assertThat(actual.get().getEmail()).isEqualTo(expectedEmail);
    }
}