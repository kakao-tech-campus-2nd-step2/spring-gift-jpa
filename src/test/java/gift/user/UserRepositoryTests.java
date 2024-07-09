package gift.user;

import gift.user.infrastructure.persistence.JpaUserRepository;
import gift.user.infrastructure.persistence.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Test
    public void saveTest() {
        // Given
        UserEntity user = new UserEntity("test");

        // When
        user = jpaUserRepository.save(user);

        // Then
        assertThat(jpaUserRepository.findById(user.getId())).isPresent();
    }

    @Test
    public void deleteTest() {
        // Given
        UserEntity user = new UserEntity("test");
        user = jpaUserRepository.save(user);

        // When
        jpaUserRepository.deleteById(user.getId());

        // Then
        assertThat(jpaUserRepository.findById(user.getId())).isEmpty();
    }

    @Test
    public void findByIdTest() {
        // Given
        UserEntity user = new UserEntity("test");
        user = jpaUserRepository.save(user);

        // When
        UserEntity foundUser = jpaUserRepository.findById(user.getId()).get();

        // Then
        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser.getName()).isEqualTo(user.getName());
    }
}
