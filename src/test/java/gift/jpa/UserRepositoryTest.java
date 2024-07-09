package gift.jpa;

import gift.user.User;
import gift.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("사용자 저장 테스트")
    void save() {
        User expected = new User("email@kakao.com",
                "passwordForTest",
                "myNickName");

        User actual = userRepository.save(expected);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
                () -> assertThat(actual.getNickname()).isEqualTo(expected.getNickname())
        );
    }

    @Test
    @DisplayName("email로 사용자 조회 테스트")
    void findByEmail() {
        User expected = new User("email@kakao.com",
                "passwordForTest",
                "myNickName");
        userRepository.save(expected);
        User actual = userRepository.findByEmail(expected.getEmail()).orElseThrow();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
                () -> assertThat(actual.getNickname()).isEqualTo(expected.getNickname())
        );
    }

    @Test
    @DisplayName("중복된 email로 저장 테스트")
    void saveDuplicatedEmail() {
        User user1 = new User("email@kakao.com",
                "passwordForTest",
                "myNickName");
        User user2 = new User("email@kakao.com",
                "passwordForTest2",
                "myNickName2");

        userRepository.save(user1);
        assertThrows(DataIntegrityViolationException.class,
                () -> userRepository.save(user2));
    }

}
