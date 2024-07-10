package gift.main.service;

import gift.main.dto.UserJoinRequest;
import gift.main.Exception.CustomException;
import gift.main.repository.UserRepository;
import gift.main.util.JwtUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void 이메일로가입() {
        UserJoinRequest user = new UserJoinRequest("진서현", "jin1228@g.mail", "1234", "USER");
        Assertions.assertThatCode(() -> {
            userService.joinUser(user);
        }).doesNotThrowAnyException();
    }

    @Test
    @Transactional
    public void 이미존재하는이메일로가입() {
        UserJoinRequest user = new UserJoinRequest("진서현", "jin1228@g.mail", "1234", "USER");
        userService.joinUser(user);
        UserJoinRequest duplicateUser = new UserJoinRequest("진서경", "jin1228@g.mail", "1234", "USER");

        Assertions.assertThatThrownBy(() -> {
                    userService.joinUser(duplicateUser);
                })
                .isInstanceOf(CustomException.class);
    }



}