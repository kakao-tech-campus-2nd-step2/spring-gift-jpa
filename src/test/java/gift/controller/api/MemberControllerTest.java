package gift.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.MemberRequest;
import gift.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입")
    void registerMember() throws Exception {
        //Given
        MemberRequest registerRequest = new MemberRequest("member1@gmail.com", "1234");
        String requestJson = objectMapper.writeValueAsString(registerRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/members/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("token").exists()
                );
    }

    @Test
    @DisplayName("로그인")
    void loginMember() throws Exception {
        //Given
        memberService.registerMember("member1@gmail.com", "1234");

        MemberRequest loginReuqest = new MemberRequest("member1@gmail.com", "1234");
        String requestJson = objectMapper.writeValueAsString(loginReuqest);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("token").exists()
                );
    }

    @Test
    @DisplayName("사용중인 이메일로 회원가입")
    void duplicatedEmailRegister() throws Exception {
        //Given
        memberService.registerMember("member1@gmail.com", "1234");

        MemberRequest duplicatedEmailRegisterRequest = new MemberRequest("member1@gmail.com", "1234");
        String requestJson = objectMapper.writeValueAsString(duplicatedEmailRegisterRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/members/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                //Then
                .andExpectAll(
                        status().isConflict(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("title").value("member1@gmail.com already in use")
                );
    }

    @Test
    @DisplayName("로그인 실패(이메일 || 비번 틀림)")
    void loginFail() throws Exception {
        //Given
        MemberRequest wrongInfoRequest = new MemberRequest("member1@gmail.com", "999999");
        String requestJson = objectMapper.writeValueAsString(wrongInfoRequest);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                //Then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("title").value("Member not found")
                );
    }

}
