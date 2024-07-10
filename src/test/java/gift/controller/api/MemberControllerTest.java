package gift.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.MemberRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerMember() throws Exception {
        //Given
        MemberRequest memberRequest = new MemberRequest("member1@gmail.com", "1234");
        String requestJson = objectMapper.writeValueAsString(memberRequest);

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
    void loginMember() throws Exception {
        //Given
        MemberRequest memberRequest = new MemberRequest("member1@gmail.com", "1234");
        String requestJson = objectMapper.writeValueAsString(memberRequest);

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
    void duplicatedEmailRegister() throws Exception {
        //Given
        MemberRequest memberRequest = new MemberRequest("member1@gmail.com", "1234");
        String requestJson = objectMapper.writeValueAsString(memberRequest);

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
    void loginFail() throws Exception {
        //Given
        MemberRequest wrongInfoRequest = new MemberRequest("member1@gmail.com", "999999");
        String requestJson = objectMapper.writeValueAsString(wrongInfoRequest);

        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                //Then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("title").value("Member not found")
                )
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

    }

}
