package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.domain.Product;
import gift.domain.User;
import gift.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        // given
        User user = new User(1L, "kakao", "kakao@google.com", "password", "BE");
        // when
        User savedUser = userRepository.save(user);
        // then
        Assertions.assertAll(
            () -> assertThat(savedUser.getId()).isNotNull(),
            () -> assertThat(savedUser.getEmail()).isEqualTo(user.getEmail())
        );
    }

    @Test
    void findbyid() {
        // given
        Long id = 2L;
        User user1 = new User(1L, "kakao", "kakao@google.com", "password", "BE");
        User user2 = new User(2L, "name", "name@google.com", "password", "BE");
        userRepository.save(user1);
        userRepository.save(user2);
        // when
        Optional<User> findUser = userRepository.findById(id);
        Long findId = findUser.get().getId();;
        // then
        assertThat(findId).isEqualTo(id);
    }

    @Test
    void deletebyid() {
        // given
        User user1 = new User(1L, "kakao", "kakao@google.com", "password", "BE");
        User user2 = new User(2L, "name", "name@google.com", "password", "BE");
        userRepository.save(user1);
        userRepository.save(user2);

        // when
        userRepository.deleteById(1L);
        List<User> savedUser = userRepository.findAll();

        // then
        assertThat(savedUser.size()).isEqualTo(1);

    }




}
