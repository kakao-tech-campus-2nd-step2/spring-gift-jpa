package gift.member.presentation;

import gift.auth.TokenService;
import gift.member.application.MemberResponse;
import gift.member.application.MemberService;
import gift.member.presentation.request.MemberJoinRequest;
import gift.member.presentation.request.MemberLoginRequest;
import gift.member.presentation.request.MemberUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private TokenService tokenService;

    private String email;
    private String password;
    private String token;

    @BeforeEach
    void setUp() {
        email = "test@example.com";
        password = "password";
        token = "testToken";
    }

    @Test
    void 회원가입_테스트() throws Exception {
        MemberJoinRequest request = new MemberJoinRequest(email, password);

        Mockito.when(memberService.join(any())).thenReturn(email);
        Mockito.when(tokenService.createToken(email)).thenReturn(token);

        mockMvc.perform(post("/api/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + token));
    }

    @Test
    void 로그인_테스트() throws Exception {
        MemberLoginRequest request = new MemberLoginRequest(email, password);

        Mockito.when(memberService.login(any())).thenReturn(email);
        Mockito.when(tokenService.createToken(email)).thenReturn(token);

        mockMvc.perform(post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + token));
    }

    @Test
    void 이메일로_찾기_테스트() throws Exception {
        MemberResponse response = new MemberResponse(email, password);

        Mockito.when(memberService.findByEmail(eq(email))).thenReturn(response);

        mockMvc.perform(get("/api/member/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password));
    }

    @Test
    void 전체_회원_찾기_테스트() throws Exception {
        MemberResponse response1 = new MemberResponse(email, password);
        MemberResponse response2 = new MemberResponse("test2@example.com", "password2");

        Mockito.when(memberService.findAll()).thenReturn(Arrays.asList(response1, response2));

        mockMvc.perform(get("/api/member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(email))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }

    @Test
    void 회원_업데이트_테스트() throws Exception {
        String newPassword = "newPassword";
        MemberUpdateRequest request = new MemberUpdateRequest(newPassword);

        mockMvc.perform(put("/api/member/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"" + newPassword + "\"}"))
                .andExpect(status().isOk());

        Mockito.verify(memberService).update(any());
    }

    @Test
    void 회원_삭제_테스트() throws Exception {
        mockMvc.perform(delete("/api/member/{email}", email))
                .andExpect(status().isOk());

        Mockito.verify(memberService).delete(eq(email));
    }
}
