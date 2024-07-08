package gift.domain.user.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.user.entity.Role;
import gift.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserDaoTest {

    @Autowired
    private UserDao userDao;
    

    @Test
    @DisplayName("DB 사용자 등록")
    void insert() {
        // given
        User user = new User(null, "testUser", "test123@test.com", "test123", Role.USER);

        // when
        User savedUser = userDao.insert(user);

        // then
        assertAll(
            () -> assertThat(savedUser).isNotNull(),
            () -> assertThat(savedUser.getId()).isNotNull(),
            () -> assertThat(savedUser.getName()).isEqualTo(user.getName()),
            () -> assertThat(savedUser.getEmail()).isEqualTo(user.getEmail()),
            () -> assertThat(savedUser.getPassword()).isEqualTo(user.getPassword()),
            () -> assertThat(savedUser.getRole()).isEqualTo(user.getRole())
        );
    }

    @Test
    @DisplayName("DB 사용자 이메일로 조회")
    void findByEmail() {
        // given
        User user = new User(null, "testUser", "test123@test.com", "test123", Role.USER);
        User savedUser = userDao.insert(user);

        // when
        User foundUser = userDao.findByEmail(savedUser.getEmail()).get();

        // then
        assertAll(
            () -> assertThat(foundUser).isNotNull(),
            () -> assertThat(foundUser.getId()).isEqualTo(savedUser.getId()),
            () -> assertThat(foundUser.getName()).isEqualTo(savedUser.getName()),
            () -> assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail()),
            () -> assertThat(foundUser.getPassword()).isEqualTo(savedUser.getPassword()),
            () -> assertThat(foundUser.getRole()).isEqualTo(savedUser.getRole())
        );
    }

    @Test
    @DisplayName("DB 사용자 ID로 조회")
    void findById() {
        // given
        User user = new User(null, "testUser", "test123@test.com", "test123", Role.USER);
        User savedUser = userDao.insert(user);

        // when
        User foundUser = userDao.findById(savedUser.getId()).get();

        // then
        assertAll(
            () -> assertThat(foundUser).isNotNull(),
            () -> assertThat(foundUser.getId()).isEqualTo(savedUser.getId()),
            () -> assertThat(foundUser.getName()).isEqualTo(savedUser.getName()),
            () -> assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail()),
            () -> assertThat(foundUser.getPassword()).isEqualTo(savedUser.getPassword()),
            () -> assertThat(foundUser.getRole()).isEqualTo(savedUser.getRole())
        );
    }

    @Test
    @DisplayName("DB 사용자 정보 수정")
    void update() {
        // given
        User user1 = new User(null, "testUser1", "test123@test.com", "test123", Role.USER);
        User savedUser = userDao.insert(user1);
        User user2 = new User(savedUser.getId(), "testUser2", savedUser.getEmail(), "test456", Role.ADMIN);


        // when
        User updatedUser = userDao.update(user2).get();

        // then
        assertAll(
            () -> assertThat(updatedUser).isNotNull(),
            () -> assertThat(updatedUser.getId()).isEqualTo(savedUser.getId()),
            () -> assertThat(updatedUser.getName()).isEqualTo(user2.getName()),
            () -> assertThat(updatedUser.getEmail()).isEqualTo(savedUser.getEmail()),
            () -> assertThat(updatedUser.getPassword()).isEqualTo(user2.getPassword()),
            () -> assertThat(updatedUser.getRole()).isEqualTo(user2.getRole())
        );
    }

    @Test
    @DisplayName("DB 사용자 삭제")
    void delete() {
        // given
        User user = new User(null, "testUser", "test123@test.com", "test123", Role.USER);
        User savedUser = userDao.insert(user);


        // when
        userDao.delete(savedUser.getEmail());

        // then
        assertThat(userDao.findByEmail(savedUser.getEmail())).isEmpty();
    }
}