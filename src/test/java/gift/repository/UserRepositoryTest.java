package gift.repository;


import gift.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetIdByEmailPassword(){
        //given
        String email = "abc@naver.com";
        String password = "123";
        User expected = new User();
        expected.setEmail(email);
        expected.setPassword(password);
        userRepository.save(expected);
        //when
        Long actualId = userRepository.getIdByEmailPassword(email, password);
        //then
        Assertions.assertThat(actualId).isEqualTo(expected.getId());
    }
}
