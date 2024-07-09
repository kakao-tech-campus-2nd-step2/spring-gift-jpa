package gift.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gift.model.JwtProvider;
import gift.model.User;
import gift.model.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("회원가입 테스트[정상]")
    void signUp() throws Exception {
        //given
        final String username = "test@naver.com";
        final String password = "test1234";
        final String request = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        final User savedUser = new User(1L, username, password);

        //when
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        when(userRepository.findByUsernameAndPassword(username, password))
                .thenReturn(Optional.of(savedUser));
        when(jwtProvider.generateToken(savedUser))
                .thenReturn("testToken");

        //then
        mockMvc.perform(post("/api/users/sign-up")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("token").value("testToken"));
    }

    @Test
    @DisplayName("로그인 테스트[정상]]")
    void signIn() throws Exception {
        //given
        final String username = "test@naver.com";
        final String password = "test1234";
        final String request = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        final User savedUser = new User(1L, username, password);

        //when
        when(userRepository.findByUsernameAndPassword(username, password))
                .thenReturn(Optional.of(savedUser));
        when(jwtProvider.generateToken(savedUser))
                .thenReturn("testToken");

        //then
        mockMvc.perform(post("/api/users/sign-in")
                        .contentType("application/json")
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").value("testToken"));
    }
}