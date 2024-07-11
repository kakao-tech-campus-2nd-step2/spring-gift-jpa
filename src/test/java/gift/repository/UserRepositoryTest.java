package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.model.user.User;
import gift.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setup() {
        user1 = new User("kakao", "kakao@google.com", "password", "BE");
        user2 = new User("name", "name@google.com", "password", "BE");
        userRepository.save(user1);
        userRepository.save(user2);
    }
    @DisplayName("회원 정보 저장 테스트")
    @Test
    void save() {
        // given
        User user3 = new User("yj", "yj@google.com", "password", "BE");
        // when
        User savedUser = userRepository.save(user3);
        // then
        Assertions.assertAll(
            () -> assertThat(savedUser.getId()).isNotNull(),
            () -> assertThat(savedUser.getEmail()).isEqualTo(user3.getEmail())
        );
    }

    @DisplayName("id에 따른 회원 찾기 테스트")
    @Test
    void findbyid() {
        // given
        Long id = user2.getId();

        // when
        Optional<User> findUser = userRepository.findById(id);
        Long findId = findUser.get().getId();;
        // then
        assertThat(findId).isEqualTo(id);
    }


}
