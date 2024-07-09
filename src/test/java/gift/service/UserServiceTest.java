package gift.service;

import gift.dto.UserLoginDto;
import gift.dto.UserRegisterDto;
import gift.dto.UserResponseDto;
import gift.entity.User;
import gift.exception.BusinessException;
import gift.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 사용자_등록() {
        UserRegisterDto registerDto = new UserRegisterDto("test@example.com", "password");
        UserResponseDto createdUser = userService.registerUser(registerDto);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals("test@example.com", createdUser.getEmail());
    }

    @Test
    public void 사용자_조회() {
        User user = new User("test@example.com", "password");
        userRepository.save(user);

        UserResponseDto retrievedUser = userService.getUserById(user.getId());

        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
    }

    @Test
    public void 사용자_수정() {
        User originalUser = new User("test@example.com", "password");
        userRepository.save(originalUser);

        UserRegisterDto updateDto = new UserRegisterDto("updated@example.com", "newpassword");
        UserResponseDto updatedUser = userService.updateUser(originalUser.getId(), updateDto);

        assertNotNull(updatedUser);
        assertEquals(originalUser.getId(), updatedUser.getId());
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    public void 사용자_삭제() {
        User user = new User("test@example.com", "password");
        userRepository.save(user);

        userService.deleteUser(user.getId());

        assertThrows(BusinessException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    public void 로그인_성공() {
        User user = new User("test@example.com", "password");
        userRepository.save(user);

        UserLoginDto loginDto = new UserLoginDto("test@example.com", "password");
        String token = userService.loginUser(loginDto);

        assertNotNull(token);
    }

    @Test
    public void 로그인_실패() {
        User user = new User("test@example.com", "password");
        userRepository.save(user);

        UserLoginDto loginDto = new UserLoginDto("test@example.com", "wrongpassword");

        assertThrows(BusinessException.class, () -> userService.loginUser(loginDto));
    }
}
