package gift.user;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import gift.user.model.UserRepository;
import gift.user.model.dto.AppUser;
import gift.user.model.dto.Role;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        AppUser appUser = new AppUser("aa@kakao.com", "1234", Role.USER, "aaaa");
        AppUser savedAppUser = userRepository.save(appUser);
        assertThat(appUser).isEqualTo(savedAppUser);
    }

    @Test
    public void testFindByEmailAndIsActiveTrue() {
        AppUser appUser = new AppUser("aa@kakao.com", "1234", Role.USER, "aaaa");
        userRepository.save(appUser);

        Optional<AppUser> foundUser = userRepository.findByEmailAndIsActiveTrue(appUser.getEmail());
        assertThat(foundUser).isPresent();
        assertThat(appUser).isEqualTo(foundUser.get());
    }

    @Test
    public void testFindByEmailAndIsActiveTrueIfUserFalse() {
        AppUser appUser = new AppUser("aa@kakao.com", "1234", Role.USER, "aaaa");
        appUser.setIsActive(false);
        userRepository.save(appUser);

        Optional<AppUser> foundUser = userRepository.findByEmailAndIsActiveTrue(appUser.getEmail());
        assertThat(foundUser).isEmpty();
    }

    @Test
    public void testFindByIdAndIsActiveTrue() {
        AppUser appUser = new AppUser("aa@kakao.com", "1234", Role.USER, "aaaa");
        userRepository.save(appUser);

        Optional<AppUser> foundUser = userRepository.findByIdAndIsActiveTrue(appUser.getId());
        assertThat(foundUser).isPresent();
        assertThat(appUser).isEqualTo(foundUser.get());
    }

    @Test
    public void testUpdatePassword() {
        AppUser appUser = new AppUser("aa@kakao.com", "1234", Role.USER, "aaaa");
        userRepository.save(appUser);

        appUser.setPassword("1111");
        userRepository.save(appUser);
        Optional<AppUser> updatedUser = userRepository.findById(appUser.getId());
        assertThat(updatedUser.get().getPassword()).isEqualTo("1111");
    }

    @Test
    public void testUniqueEmailConstraint() {
        AppUser appUser = new AppUser("aa@kakao.com", "1234", Role.USER, "aaaa");
        userRepository.save(new AppUser("aa@kakao.com", "1234", Role.USER, "aaaa"));
        assertThatThrownBy(() ->
                userRepository.save(new AppUser("aa@kakao.com", "1234", Role.USER, "aaaa"))
        ).isInstanceOf(DataIntegrityViolationException.class);
    }
}
