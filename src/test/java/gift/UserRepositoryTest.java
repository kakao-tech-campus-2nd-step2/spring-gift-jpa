package gift;

import gift.entity.User;
import gift.repository.UserRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    private UserRepositoryInterface users;

    public UserRepositoryTest(UserRepositoryInterface userRepository) {
        users= userRepository;
    }

    @Test
    public void save(User user) {
        users.save(user);
    }

}
