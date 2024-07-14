package gift;

import gift.entity.User;
import gift.repository.UserRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryInterfaceTest {
    private UserRepositoryInterface users;

    public UserRepositoryInterfaceTest(UserRepositoryInterface userRepositoryInterface) {
        users= userRepositoryInterface;
    }

    @Test
    public void save(User user) {
        users.save(user);
    }

}
