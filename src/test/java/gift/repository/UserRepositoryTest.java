package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("save 테스트")
    public void saveTest() {
        User expected = new User("test@abc.com", "password");
        User actual = userRepository.save(expected);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }

    @Test
    @DisplayName("이메일 기반으로 유저 찾기 테스트")
    public void findByEmailTest() {
        User user = new User("test@abc.com", "password");
        User actual = userRepository.save(user);
        User expected = userRepository.findByEmail(user.getEmail());

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.samePassword(expected.getPassword())).isTrue();
    }

    @Test
    @DisplayName("저장되어있지 않은 이메일로 유저를 찾을 때 Null을 리턴하는지 테스트")
    public void edgeCaseTest() {
        String NotSavedEmail = "notSavedEmail@abc.com";

        assertThat(userRepository.findByEmail(NotSavedEmail)).isNull();
    }
}
