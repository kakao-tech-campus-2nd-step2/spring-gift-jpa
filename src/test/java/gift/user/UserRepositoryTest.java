package gift.user;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import gift.user.model.UserRepository;
import gift.user.model.dto.Role;
import gift.user.model.dto.User;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User("aa@kakao.com", "1234", Role.USER, "aaaa");
        User savedUser = userRepository.save(user);
        assertThat(user).isEqualTo(savedUser);
    }

    @Test
    public void testFindByEmailAndIsActiveTrue() {
        User user = new User("aa@kakao.com", "1234", Role.USER, "aaaa");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmailAndIsActiveTrue(user.getEmail());
        assertThat(foundUser).isPresent();
        assertThat(user).isEqualTo(foundUser.get());
    }

    @Test
    public void testFindByEmailAndIsActiveTrueIfUserFalse() {
        User user = new User("aa@kakao.com", "1234", Role.USER, "aaaa");
        user.setIsActive(false);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmailAndIsActiveTrue(user.getEmail());
        assertThat(foundUser).isEmpty();
    }

    @Test
    public void testFindByIdAndIsActiveTrue() {
        User user = new User("aa@kakao.com", "1234", Role.USER, "aaaa");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByIdAndIsActiveTrue(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(user).isEqualTo(foundUser.get());
    }

    @Test
    public void testUpdatePassword() {
        User user = new User("aa@kakao.com", "1234", Role.USER, "aaaa");
        userRepository.save(user);

        user.setPassword("1111");
        userRepository.save(user);
        Optional<User> updatedUser = userRepository.findById(user.getId());
        assertThat(updatedUser.get().getPassword()).isEqualTo("1111");
    }

    @Test
    public void testUniqueEmailConstraint() {
        User user = new User("aa@kakao.com", "1234", Role.USER, "aaaa");
        userRepository.save(new User("aa@kakao.com", "1234", Role.USER, "aaaa"));
        assertThatThrownBy(() ->
                userRepository.save(new User("aa@kakao.com", "1234", Role.USER, "aaaa"))
        ).isInstanceOf(DataIntegrityViolationException.class);
    }
}
