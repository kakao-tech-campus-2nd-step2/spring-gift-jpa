package gift.domain.user.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.user.entity.Role;
import gift.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("사용자 등록 테스트")
    void save() {
        // given
        User expected = new User(null, "testUser", "test@test.com", "test123", Role.USER);

        // when
        User actual = userJpaRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
            () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
            () -> assertThat(actual.getRole()).isEqualTo(expected.getRole())
        );
    }

    @Test
    @DisplayName("이메일로 사용자 검색 테스트")
    void findByEmail() {
        // given
        User expected = new User(null, "testUser", "test@test.com", "test123", Role.USER);
        userJpaRepository.save(expected);

        // when
        User actual = userJpaRepository.findByEmail("test@test.com").get();

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
            () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
            () -> assertThat(actual.getRole()).isEqualTo(expected.getRole())
        );
    }
}