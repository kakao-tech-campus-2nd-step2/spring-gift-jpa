package gift;

import gift.Entity.Users;
import gift.Model.User;
import gift.Repository.UsersJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UsersJpaRepository usersJpaRepository;

    @Test
    public void testGetAllUsers() {
        User user1 = new User(1L, "1234@naver.com", "1234", "1234", false);
        User user2 = new User(2L, "5678@naver.com", "5678", "5678", false);

        Users users1 = Users.createUsers(user1);
        Users users2 = Users.createUsers(user2);

        usersJpaRepository.save(users1);
        usersJpaRepository.save(users2);

        assertThat(usersJpaRepository.findAll()).isNotEmpty();
    }

    @Test
    public void testGetUserById() {
        User user1 = new User(1L, "1234@naver.com", "1234", "1234", false);
        Users users1 = Users.createUsers(user1);
        usersJpaRepository.save(users1);

        Optional<Users> foundUserOptional = usersJpaRepository.findById(users1.getId());
        assertThat(foundUserOptional).isNotNull();

        foundUserOptional.ifPresent(foundUser -> {
            assertThat(foundUser.getEmail()).isEqualTo(users1.getEmail());
            assertThat(foundUser.getPassword()).isEqualTo(users1.getPassword());
        });

    }

    @Test
    public void testSaveUser() {
        User user1 = new User(1L, "1234@naver.com", "1234", "1234", false);
        Users users1 = Users.createUsers(user1);
        Users savedUser = usersJpaRepository.save(users1);
        assertThat(savedUser.getEmail()).isEqualTo(users1.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(users1.getPassword());

    }

    @Test
    public void testDeleteUser() {
        User user1 = new User(1L, "1234@naver.com", "1234", "1234", false);
        Users users1 = Users.createUsers(user1);
        usersJpaRepository.save(users1);

        usersJpaRepository.delete(users1);

        assertThat(usersJpaRepository.findById(users1.getId())).isEmpty();
    }

    @Test
    public void testUpdateUser() {
        User user1 = new User(1L, "1234@naver.com", "1234", "1234", false);
        Users users1 = Users.createUsers(user1);
        usersJpaRepository.save(users1);
    }

}
