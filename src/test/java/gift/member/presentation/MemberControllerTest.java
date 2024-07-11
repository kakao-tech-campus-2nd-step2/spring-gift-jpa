package gift.member.presentation;

import gift.auth.TokenService;
import gift.member.application.MemberResponse;
import gift.member.application.MemberService;
import gift.member.application.command.MemberUpdateCommand;
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
        MemberJoinRequest request = new MemberJoinRequest(email, password);

        Mockito.when(memberService.join(request.toCommand())).thenReturn(memberId);
        Mockito.when(tokenService.createToken(memberId)).thenReturn(token);

        mockMvc.perform(post("/api/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + token));
    }

    @Test
    void 로그인_테스트() throws Exception {
        MemberLoginRequest request = new MemberLoginRequest(email, password);

        // Todo eq랑 그냥 넘겼을때 비교
        Mockito.when(memberService.login(request.toCommand())).thenReturn(memberId);
        Mockito.when(tokenService.createToken(memberId)).thenReturn(token);

        mockMvc.perform(post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + token));
    }

    @Test
    void 아이디로_찾기_테스트() throws Exception {
        MemberResponse response = new MemberResponse(memberId, email, password);

        Mockito.when(memberService.findById(eq(memberId))).thenReturn(response);

        mockMvc.perform(get("/api/member/{id}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password));
    }

    @Test
    void 전체_회원_찾기_테스트() throws Exception {
        MemberResponse response1 = new MemberResponse(memberId, email, password);
        MemberResponse response2 = new MemberResponse(2L, "test2@example.com", "password2");

        Mockito.when(memberService.findAll()).thenReturn(Arrays.asList(response1, response2));

        mockMvc.perform(get("/api/member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(email))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }

    @Test
    void 회원_업데이트_테스트() throws Exception {
        String newEmail = "test2@example.com";
        String newPassword = "newPassword";

        MemberUpdateCommand expectedCommand = new MemberUpdateCommand(memberId, newEmail, newPassword);

        mockMvc.perform(put("/api/member/{id}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + newEmail + "\", \"password\":\"" + newPassword + "\"}"))
                .andExpect(status().isOk());

        // Mockito의 eq 매처를 사용하여 전달된 인자를 검증
        Mockito.verify(memberService).update(eq(expectedCommand));
    }


    @Test
    void 회원_삭제_테스트() throws Exception {
        mockMvc.perform(delete("/api/member/{id}", memberId))
                .andExpect(status().isOk());

        Mockito.verify(memberService).delete(eq(memberId));
    }
}