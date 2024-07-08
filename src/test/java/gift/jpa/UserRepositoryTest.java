package gift.jpa;

import gift.product.Product;
import gift.product.ProductRepository;
import gift.user.User;
import gift.user.UserDTO;
import gift.user.UserRepository;
import gift.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void save(){
        User expected = new User("email@kakao.com",
                                "passwordForTest",
                                "myNickName");

        User actual = userRepository.save(expected);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
                () -> assertThat(actual.getNickname()).isEqualTo(expected.getNickname())
        );
    }
    @Test
    void findByEmail(){
        User expected = new User("email@kakao.com",
                "passwordForTest",
                "myNickName");
        userRepository.save(expected);
        User actual = userRepository.findByEmail(expected.getEmail()).orElseThrow();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
                () -> assertThat(actual.getNickname()).isEqualTo(expected.getNickname())
        );
    }

}
