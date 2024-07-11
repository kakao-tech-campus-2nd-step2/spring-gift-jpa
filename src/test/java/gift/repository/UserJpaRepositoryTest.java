package gift.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserJpaRepositoryTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @BeforeEach
    void before(){
        String email = "aaa@gmail.com";
        String password = "123";
        String accessToken = "aaa";
        User user = new User(email,password,accessToken);
        userJpaRepository.save(user);
    }

    @Test
    @DisplayName("이메일 존재확인 테스트")
    @Order(1)
    void existsByEmail() {
        String email = "aaa@gmail.com";
        boolean result = userJpaRepository.existsByEmail(email);
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("이메일과 비밀번호로 유저 검색 테스트")
    @Order(2)
    void findByEmailAndPassword() {
        String email = "aaa@gmail.com";
        String password = "123";
        User user = userJpaRepository.findByEmailAndPassword(email, password).get();
        assertAll(
            () -> assertThat(user.getId()).isNotNull(),
            () -> assertThat(user.getEmail()).isEqualTo(email),
            () -> assertThat(user.getPassword()).isEqualTo(password)
        );
    }

    @Test
    @DisplayName("토큰으로 유저 검색 테스트")
    @Order(3)
    void findByAccessToken() {
    }
}