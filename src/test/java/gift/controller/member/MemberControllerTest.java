package gift.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.MemberRequest;
import gift.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    MemberRequest newMember = new MemberRequest("zzoe2346@github.com", "1234");

    @Test
    @DisplayName("정상 회원가입 테스트")
    void registerMember() throws Exception {
        String newMemberJson = objectMapper.writeValueAsString(newMember);
        MvcResult mvcResult = mockMvc.perform(post("/members/register")
                        .content(newMemberJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("정상 로그인 테스트")
    void loginMember() throws Exception {
        String newMemberJson = objectMapper.writeValueAsString(newMember);
        MvcResult mvcResult = mockMvc.perform(post("/members/login")
                        .content(newMemberJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("존재하는 이메일로 회원가입 시도할때 테스트")
    void emailDuplicate() throws Exception {
        String duplicatedMemberEmailJson = objectMapper.writeValueAsString(newMember);
        MvcResult mvcResult = mockMvc.perform(post("/members/register")
                        .content(duplicatedMemberEmailJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
