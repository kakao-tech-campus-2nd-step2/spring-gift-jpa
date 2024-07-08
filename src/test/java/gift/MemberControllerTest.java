package gift;

import gift.controller.MemberController;
import gift.model.Member;
import gift.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MemberService memberService;

  private Member member;
  private String token;

  @BeforeEach
  void setUp() {
    member = new Member();
    member.setId(1L);
    member.setEmail("test@example.com");
    member.setPassword("password");

    token = "test-token";

    Mockito.when(memberService.register(any(Member.class))).thenReturn(token);
    Mockito.when(memberService.login(anyString(), anyString())).thenReturn(token);
  }

  @Test
  void testRegister() throws Exception {
    String memberJson = "{\"email\":\"test@example.com\", \"password\":\"password\"}";

    mockMvc.perform(post("/members/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(memberJson))
            .andExpect(status().isOk())
            .andExpect(content().string(token));
  }

  @Test
  void testLogin() throws Exception {
    String memberJson = "{\"email\":\"test@example.com\", \"password\":\"password\"}";

    mockMvc.perform(post("/members/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(memberJson))
            .andExpect(status().isOk())
            .andExpect(content().string(token));
  }
}
