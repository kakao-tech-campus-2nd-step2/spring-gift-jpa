package gift.member.presentation;

import gift.auth.TokenService;
import gift.member.application.MemberResponse;
import gift.member.application.MemberService;
import gift.member.application.command.MemberEmailUpdateCommand;
import gift.member.application.command.MemberPasswordUpdateCommand;
import gift.member.presentation.request.MemberJoinRequest;
import gift.member.presentation.request.MemberLoginRequest;
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

    private Long memberId;
    private String email;
    private String password;
    private String token;

    @BeforeEach
    void setUp() {
        memberId = 1L;
        email = "test@example.com";
        password = "password";
        token = "testToken";
    }

    @Test
    void 회원가입_테스트() throws Exception {
        // Given
        MemberJoinRequest request = new MemberJoinRequest(email, password);
        Mockito.when(memberService.join(request.toCommand())).thenReturn(memberId);
        Mockito.when(tokenService.createToken(memberId)).thenReturn(token);

        // When & Then
        mockMvc.perform(post("/api/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + token));
    }

    @Test
    void 로그인_테스트() throws Exception {
        // Given
        MemberLoginRequest request = new MemberLoginRequest(email, password);
        Mockito.when(memberService.login(request.toCommand())).thenReturn(memberId);
        Mockito.when(tokenService.createToken(memberId)).thenReturn(token);

        // When & Then
        mockMvc.perform(post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + token));
    }

    @Test
    void 아이디로_찾기_테스트() throws Exception {
        // Given
        MemberResponse response = new MemberResponse(memberId, email, password);
        Mockito.when(memberService.findById(eq(memberId))).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/member/{id}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password));
    }

    @Test
    void 전체_회원_찾기_테스트() throws Exception {
        // Given
        MemberResponse response1 = new MemberResponse(memberId, email, password);
        MemberResponse response2 = new MemberResponse(2L, "test2@example.com", "password2");
        Mockito.when(memberService.findAll()).thenReturn(Arrays.asList(response1, response2));

        // When & Then
        mockMvc.perform(get("/api/member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(email))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }

    @Test
    void 이메일_업데이트_테스트() throws Exception {
        // Given
        String newEmail = "test2@example.com";
        MemberEmailUpdateCommand expectedCommand = new MemberEmailUpdateCommand(memberId, newEmail);

        // When & Then
        mockMvc.perform(put("/api/member/{id}/email", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + newEmail + "\"}"))
                .andExpect(status().isOk());

        Mockito.verify(memberService).updateEmail(eq(expectedCommand));
    }

    @Test
    void 비밀번호_업데이트_테스트() throws Exception {
        // Given
        String newPassword = "newPassword";
        MemberPasswordUpdateCommand expectedCommand = new MemberPasswordUpdateCommand(memberId, newPassword);

        // When & Then
        mockMvc.perform(put("/api/member/{id}/password", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"" + newPassword + "\"}"))
                .andExpect(status().isOk());

        Mockito.verify(memberService).updatePassword(eq(expectedCommand));
    }

    @Test
    void 회원_삭제_테스트() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/member/{id}", memberId))
                .andExpect(status().isOk());

        Mockito.verify(memberService).delete(eq(memberId));
    }
}
