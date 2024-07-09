package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.Product;
import gift.domain.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaUserRepositoryTest {
    @Autowired
    private final JpaUserRepository jpaUserRepository;

    public JpaUserRepositoryTest(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }
    private Long insertUser(User user){
        return  jpaUserRepository.save(user).getId();
    }
    @Test
    void 회원_가입() {
        //given
        User user = new User("www.naver.com", "1234", "일반");
        //when
        Long insertUserId = insertUser(user);
        //then
        User findUser = jpaUserRepository.findById(insertUserId).get();
        assertAll(
            () -> assertThat(findUser).isEqualTo(user)
        );
    }
    @Test
    void 회원_조회(){
        //given
        User user = new User("www.naver.com", "1234", "일반");
        Long insertUserId = insertUser(user);
        //when
        User findUser = jpaUserRepository.findById(insertUserId).get();
        User findUserByEmail = jpaUserRepository.findByEmail("www.naver.com").get();
        //then
        assertAll(
            () -> assertThat(findUser.getId()).isNotNull(),
            () -> assertThat(findUser.getId()).isEqualTo(insertUserId),
            () -> assertThat(findUser.getEmail()).isEqualTo("www.naver.com"),
            () -> assertThat(findUser.getPassword()).isEqualTo("1234"),
            () -> assertThat(findUser.getRole()).isEqualTo("일반"),

            () -> assertThat(findUserByEmail).isNotNull()
        );
    }
}