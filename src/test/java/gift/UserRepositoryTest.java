package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.user.User;
import gift.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 추가 테스트")
    void save(){
        //Given
        User user = new User("admin@email.com","1234");

        //When
        User actual = userRepository.save(user);

        //Then
        assertThat(actual.getEmail()).isEqualTo("admin@email.com");
        assertThat(actual.getPassword()).isEqualTo("1234");
    }

    @Test
    @DisplayName("회원 이메일과 비밀번호로 찾기")
    void existsByEmailAndPassword() {
        //Given
        User user = new User("admin@email.com","1234");
        userRepository.save(user);

        //When
        Boolean actual = userRepository.existsByEmailAndPassword(user.getEmail(), user.getPassword());

        //Then
        assertThat(actual).isEqualTo(true);
    }

    @Test
    @DisplayName("이메일과 비밀번호가 둘 다 존재하지 않는 회원을 찾으면 false 리턴")
    void notExistsByEmailAndPassword() {
        //Given
        User user = new User("admin@email.com","1234");
        userRepository.save(user);

        //When
        Boolean actual = userRepository.existsByEmailAndPassword("admin@email.com","2222");

        //Then
        assertThat(actual).isEqualTo(false);
    }

    @Test
    @DisplayName("회원 이메일로 찾기")
    void existsByEmail(){
        //Given
        User user = new User("admin@email.com","1234");
        userRepository.save(user);

        //When
        Boolean actual = userRepository.existsByEmail(user.getEmail());

        //Then
        assertThat(actual).isEqualTo(true);
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 회원을 찾으면 false 리턴")
    void notExistsByEmail(){
        //Given
        User user = new User("admin@email.com","1234");
        userRepository.save(user);

        //When
        Boolean actual = userRepository.existsByEmail("example@email.com");

        //Then
        assertThat(actual).isEqualTo(false);
    }
}
