package gift;

import gift.controller.MemberController;
import gift.model.Member;
import gift.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    member = new Member();
    member.setEmail("test@example.com");
    member.setPassword("password");
  }

  @Test
  public void testShowLoginPage() throws Exception {
    mockMvc.perform(get("/members/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(model().attributeExists("errorMessage"));
  }

  @Test
  public void testShowRegisterPage() throws Exception {
    mockMvc.perform(get("/members/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("register"))
            .andExpect(model().attributeExists("errorMessage"));
  }

  @Test
  public void testRegister() throws Exception {
    when(memberService.register(member)).thenReturn("test-token");

    mockMvc.perform(post("/members/register")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("email", "test@example.com")
                    .param("password", "password"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(model().attributeExists("message"))
            .andExpect(model().attribute("message", "Registration successful."));
  }

  @Test
  public void testRegisterWithError() throws Exception {
    when(memberService.register(any(Member.class))).thenThrow(new RuntimeException("Error during registration"));

    mockMvc.perform(post("/members/register")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("email", "test@example.com")
                    .param("password", "password"))
            .andExpect(status().isOk())
            .andExpect(view().name("register"))
            .andExpect(model().attributeExists("errorMessage"))
            .andExpect(model().attribute("errorMessage", "Error: Error during registration"));
  }

  @Test
  public void testLogin() throws Exception {
    when(memberService.login("test@example.com", "password")).thenReturn("test-token");

    mockMvc.perform(post("/members/login")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("email", "test@example.com")
                    .param("password", "password"))
            .andExpect(status().isOk())
            .andExpect(view().name("welcome"))
            .andExpect(model().attributeExists("message"))
            .andExpect(model().attribute("message", "Login successful."));
  }

  @Test
  public void testLoginWithInvalidCredentials() throws Exception {
    when(memberService.login("test@example.com", "password")).thenThrow(new IllegalArgumentException("Invalid email or password"));

    mockMvc.perform(post("/members/login")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("email", "test@example.com")
                    .param("password", "password"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(model().attributeExists("errorMessage"))
            .andExpect(model().attribute("errorMessage", "Invalid email or password"));
  }
}
