package gift.service;

import gift.dto.UserResponseDto;
import gift.entity.User;
import gift.entity.UserDao;
import gift.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 회원가입() {
        String email = "test@example.com";
        String password = "password123";
        User user = new User(email, password);
        when(userDao.selectUserByEmail(email)).thenReturn(Optional.empty());
        when(userDao.insertUser(any(User.class))).thenReturn(user);

        UserResponseDto registeredUser = userService.registerUser(email, password);

        assertNotNull(registeredUser);
        assertEquals(email, registeredUser.email);
    }

    @Test
    public void 회원가입_이미_존재하는_이메일() {
        String email = "test@example.com";
        String password = "password123";
        User user = new User(email, password);
        when(userDao.selectUserByEmail(email)).thenReturn(Optional.of(user));

        assertThrows(BusinessException.class, () -> userService.registerUser(email, password));
    }

    @Test
    public void 로그인() {
        String email = "test@example.com";
        String password = "password123";
        User user = new User(email, password);
        when(userDao.selectUserByEmail(email)).thenReturn(Optional.of(user));
        when(tokenService.generateToken(email)).thenReturn("token");


        String token = userService.loginUser(email, password);

        assertNotNull(token);
        assertEquals("token", token);
    }

    @Test
    public void 로그인_잘못된_이메일_또는_비밀번호() {
        String email = "test@example.com";
        String password = "password123";
        when(userDao.selectUserByEmail(email)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.loginUser(email, password));
    }

    @Test
    public void 모든_회원_조회() {
        User user = new User("test@example.com", "password123");
        when(userDao.selectAllUsers()).thenReturn(List.of(user));

        List<UserResponseDto> userList = userService.getAllUsers();

        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertEquals("test@example.com", userList.get(0).email);
    }

    @Test
    public void 회원_조회() {
        User user = new User("test@example.com", "password123");
        when(userDao.selectUserById(1L)).thenReturn(Optional.of(user));

        UserResponseDto retrievedUser = userService.getUserById(1L);

        assertNotNull(retrievedUser);
        assertEquals("test@example.com", retrievedUser.email);
    }

    @Test
    public void 회원_조회_없는_회원() {
        when(userDao.selectUserById(100L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.getUserById(100L));
    }

    @Test
    public void 회원_수정() {
        User existingUser = new User(1L, "test@example.com", "password123");
        when(userDao.selectUserById(1L)).thenReturn(Optional.of(existingUser));

        String newEmail = "new@example.com";
        String newPassword = "newpassword123";
        User updatedUser = new User(1L, newEmail, newPassword);
        doNothing().when(userDao).updateUser(any(User.class));

        UserResponseDto result = userService.updateUser(1L, newEmail, newPassword);

        assertNotNull(result);
        assertEquals(newEmail, result.email);
    }

    @Test
    public void 회원_수정_없는_회원() {
        String newEmail = "new@example.com";
        String newPassword = "newpassword123";
        when(userDao.selectUserById(100L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.updateUser(100L, newEmail, newPassword));
    }

    @Test
    public void 회원_삭제() {
        User user = new User(1L, "test@example.com", "password123");
        when(userDao.selectUserById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userDao).deleteUser(1L);

        userService.deleteUser(1L);

        verify(userDao, times(1)).deleteUser(1L);
    }

    @Test
    public void 회원_삭제_없는_회원() {
        when(userDao.selectUserById(100L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.deleteUser(100L));
    }
}
