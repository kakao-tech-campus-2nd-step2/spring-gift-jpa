package gift.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import gift.model.SiteUser;
import gift.repository.UserRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    @Order(1)
    @DisplayName("회원가입")
    void testUserRegistration() {
        // Given
        SiteUser user = new SiteUser();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        // When
        SiteUser savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @Order(2)
    @DisplayName("로그인")
    void testUserLogin() {
        // Given
        String username = "testuser";
        String password = "password";
        String email = "test@example.com";
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userRepository.save(user);

        // When
        Optional<SiteUser> optionalUser = userRepository.findByUsername(username);

        // Then
        assertThat(optionalUser).isPresent();
        SiteUser foundUser = optionalUser.get();
        assertThat(foundUser.getEmail()).isEqualTo(email);

    }


}
