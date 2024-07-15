package gift.main.repository;

import gift.main.entity.User;
import gift.main.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void ENUM값이_잘들어가는지_조회() {
        User user = userRepository.save(new User("testuser", "email", "1234", "admin"));
        System.out.println("Role.ADMIN.equals(user.getRole()) = " + Role.ADMIN.equals(user.getRole()));
        //Role.ADMIN.equals(user.getRole()) = true
        Assertions.assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void 모두조회() {
        //given
        userRepository.save(new User("name", "123@123", "123", "USER"));
        userRepository.save(new User("name", "1234@123", "123", "USER"));

        //when
        List<User> userList = userRepository.findAll();

        //then
        assertEquals(2, userList.size());

    }


    @Test
    public void 이메일중복허용안함() {
        //given
        userRepository.save(new User("name", "123@123", "123", "USER"));

        //then
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(new User("name112", "123@123", "123", "USER"));
        });

    }

}