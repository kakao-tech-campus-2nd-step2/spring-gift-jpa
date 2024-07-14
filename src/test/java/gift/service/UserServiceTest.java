package gift.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.dto.user.UserLoginRequest;
import gift.dto.user.UserRegisterRequest;
import gift.dto.user.UserResponse;
import gift.entity.User;
import gift.exception.InvalidTokenException;
import gift.exception.user.UserAlreadyExistException;
import gift.exception.user.UserNotFoundException;
import gift.repository.UserRepository;
import gift.util.auth.JwtUtil;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

class UserServiceTest implements AutoCloseable {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Override
    public void close() throws Exception {
        closeable.close();
    }


    @Test
    @DisplayName("register user test")
    @Transactional
    void registerUserTest() {
        //given
        UserRegisterRequest request = new UserRegisterRequest("user@email.com", "1q2w3e4r!");
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        User user = User.builder()
            .id(1L)
            .email(request.email())
            .password(request.password())
            .build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUtil.generateToken(user.getId(), user.getEmail())).thenReturn("token");

        //when
        UserResponse actual = userService.registerUser(request);

        //then
        assertThat(actual.token()).isEqualTo("token");
        verify(userRepository, times(1)).findByEmail(request.email());
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtUtil, times(1)).generateToken(user.getId(), user.getEmail());
    }

    @Test
    @DisplayName("Already Exist user registration test")
    @Transactional
    void alreadyExistUserRegistrationTest() {
        //given
        UserRegisterRequest request = new UserRegisterRequest("user1@example.com", "password1");
        when(userRepository.findByEmail(request.email())).thenReturn(
            Optional.of(User.builder().build()));

        //when&then
        assertThatThrownBy(() -> userService.registerUser(request))
            .isInstanceOf(UserAlreadyExistException.class);
        verify(userRepository, times(1)).findByEmail(request.email());
    }

    @Test
    @DisplayName("user login test")
    @Transactional
    void userLoginTest() {
        //given
        UserLoginRequest loginRequest = new UserLoginRequest("user1@example.com", "password1");
        User user = User.builder()
            .id(1L)
            .email(loginRequest.email())
            .password(loginRequest.password())
            .build();
        when(userRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password()))
            .thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user.getId(), user.getEmail())).thenReturn("token");

        //when
        UserResponse actual = userService.loginUser(loginRequest);

        //then
        assertThat(actual.token()).isEqualTo("token");
        verify(userRepository, times(1)).findByEmailAndPassword(loginRequest.email(),
            loginRequest.password());
    }

    @Test
    @DisplayName("unknown user login test")
    @Transactional
    void unknownUserLoginTest() {
        //given
        UserLoginRequest request = new UserLoginRequest("user1@email.com", "1q2w3e4r!");
        when(userRepository.findByEmailAndPassword(request.email(), request.password())).thenReturn(
            Optional.empty());

        //when & then
        assertThatThrownBy(() -> userService.loginUser(request))
            .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("wrong password login test")
    @Transactional
    void wrongPasswordLoginTest() {
        //given
        UserLoginRequest request = new UserLoginRequest("user1@email.com", "1234");
        when(userRepository.findByEmailAndPassword(request.email(), request.password())).thenReturn(
            Optional.empty());

        //when & then
        assertThatThrownBy(() -> userService.loginUser(request))
            .isInstanceOf(UserNotFoundException.class);
        verify(userRepository, times(1)).findByEmailAndPassword(request.email(),
            request.password());
    }

    @Test
    @DisplayName("get user by id test")
    void getUserByIdTest() {
        // given
        User user = User.builder()
            .id(1L)
            .email("user1@example.com")
            .password("password1")
            .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        User actual = userService.getUserById(1L);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1L);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("get user by id not found test")
    void getUserByIdNotFoundTest() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.getUserById(1L))
            .isInstanceOf(UserNotFoundException.class);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("get user id by token test")
    void getUserIdByTokenTest() {
        // given
        String token = "token";
        when(jwtUtil.extractUserId(token)).thenReturn(1L);
        when(userRepository.existsById(1L)).thenReturn(true);

        // when
        Long userId = userService.getUserIdByToken(token);

        // then
        assertThat(userId).isEqualTo(1L);
        verify(jwtUtil, times(1)).extractUserId(token);
        verify(userRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("invalid token test")
    void invalidTokenTest() {
        // given
        String token = "invalid_token";
        when(jwtUtil.extractUserId(token)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> userService.getUserIdByToken(token))
            .isInstanceOf(InvalidTokenException.class);
        verify(jwtUtil, times(1)).extractUserId(token);
    }
}
