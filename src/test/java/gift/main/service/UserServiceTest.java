package gift.main.service;

import gift.main.dto.UserJoinRequest;
import gift.main.Exception.CustomException;
import gift.main.repository.UserDao;
import gift.main.util.JwtUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private UserDao userDao;

    private UserService userService;

    //    @BeforeAll // 클래스에 하나
    @BeforeEach // 메서드마다 하나
    public void createTabel() {
        this.userDao = new UserDao(jdbcTemplate);
        this.userService = new UserService(userDao, jwtUtil);
    }

    @AfterEach // 메서드마다 하나
    public void clearTabel() {
        jdbcTemplate.execute("DELETE FROM users");
    }

    @Test
    public void 이메일로가입() {
        UserJoinRequest user = new UserJoinRequest("진서현", "jin1228@g.mail", "1234", "user");

        Assertions.assertThatCode(() -> {
            userService.joinUser(user);
        }).doesNotThrowAnyException();
    }

    @Test
    public void 이미존재하는이메일로가입() {
        UserJoinRequest user = new UserJoinRequest("진서현", "jin1228@g.mail", "1234", "user");
        userService.joinUser(user);
        UserJoinRequest duplicateUser = new UserJoinRequest("진서경", "jin1228@g.mail", "1234", "user");

        Assertions.assertThatThrownBy(() -> {
                    userService.joinUser(duplicateUser);
                })
                .isInstanceOf(CustomException.class);
    }



}