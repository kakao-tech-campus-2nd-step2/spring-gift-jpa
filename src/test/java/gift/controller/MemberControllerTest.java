package gift.controller;

import gift.dto.LoginRequest;
import gift.dto.RegisterRequest;
import gift.service.auth.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("정상적으로 회원가입 후 잘못된 패스워드로 로그인 요청하기")
    void registerAndLoginFail() throws Exception {
        var registerRequest = new RegisterRequest("테스트", "test@naver.com", "testPassword", "MEMBER");
        var loginRequest = new LoginRequest("test@naver.com", "testPassword");

        authService.register(registerRequest);
        var loginResponse = authService.login(loginRequest);

        var deleted = mockMvc.perform(delete("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + loginResponse.token()));

        deleted.andExpect(status().isNoContent());
    }
}
