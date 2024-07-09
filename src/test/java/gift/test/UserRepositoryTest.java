package gift.test;

import gift.model.SiteUser;
import gift.repository.UserRepository;
import gift.user.UserCreateForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = {"gift"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @Order(1)
    @DisplayName("회원가입")
    void testUserRegistration() {
        // Given
        SiteUser user = new SiteUser();
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("password"));
        user.setEmail("test@example.com");

        // When
        SiteUser savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @Order(2)
    @DisplayName("로그인 실패 - 잘못된 아이디 또는 비밀번호")
    void testLoginWithInvalidCredentials() throws Exception {
        // Given
        UserCreateForm form = new UserCreateForm();
        form.setUsername("invalidUser");
        form.setPassword("wrongPassword");

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content("{ \"username\": \"invalidUser\", \"password\": \"wrongPassword\" }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.error").value("Invalid login credentials"));
    }

    @Test
    @Order(3)
    @DisplayName("로그인 성공 - 유효한 아이디와 비밀번호")
    void testLoginWithValidCredentials() throws Exception {
        // Given: 로그인 성공을 위해 유효한 사용자 생성
        SiteUser user = new SiteUser();
        user.setUsername("validUser");
        user.setPassword(passwordEncoder.encode("validPassword"));
        user.setEmail("valid@example.com");
        userRepository.save(user);

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content("{ \"username\": \"validUser\", \"password\": \"validPassword\" }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists());
    }
}
