package gift.controller;

import gift.entity.Member;
import gift.service.MemberService;
import gift.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    public void registerMember() throws Exception {
        Member member = new Member();
        member.setEmail("testemail@example.com");
        member.setPassword("testPassword");

        given(memberService.save(any(Member.class))).willReturn(member);

        mockMvc.perform(post("/member/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"testemail@example.com\",\"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("success"));
    }

    @Test
    public void loginMember() throws Exception {
        Member member = new Member();
        member.setEmail("testemail@example.com");
        member.setPassword("testPassword");

        given(memberService.findByEmail(anyString())).willReturn(Optional.of(member));
        given(jwtUtil.generateToken(anyString())).willReturn("fakeToken");

        mockMvc.perform(post("/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"testemail@example.com\",\"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fakeToken"));
    }

    @Test
    public void getCurrentMember() throws Exception {
        Member member = new Member();
        member.setEmail("testemail@example.com");

        given(jwtUtil.extractEmail(anyString())).willReturn("testemail@example.com");
        given(memberService.findByEmail(anyString())).willReturn(Optional.of(member));

        mockMvc.perform(post("/member/current")
                .header("Authorization", "Bearer fakeToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testemail@example.com"));
    }
}
