package gift.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.model.Member;
import gift.service.MemberService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("test@email.com", "password");
    }

    @Test
    void registerMemberSuccess() throws Exception {
        // Given
        String token = "generatedToken";
        when(memberService.registerMember(any(Member.class))).thenReturn(Optional.of(token));

        // When & Then
        mockMvc.perform(post("/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Member registered successfully"))
            .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void registerMemberFail() throws Exception {
        // Given
        when(memberService.registerMember(any(Member.class))).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Registration failed"))
            .andExpect(jsonPath("$.errors[0]").value("email: 올바른 형식의 이메일 주소여야 합니다"));
    }

    @Test
    void loginSuccess() throws Exception {
        // Given
        String token = "loginToken";
        when(memberService.login(member.getEmail(), member.getPassword())).thenReturn(Optional.of(token));

        // When & Then
        mockMvc.perform(post("/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void loginFail() throws Exception {
        // Given
        when(memberService.login(member.getEmail(), member.getPassword())).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
            .andExpect(status().isForbidden());
    }
}