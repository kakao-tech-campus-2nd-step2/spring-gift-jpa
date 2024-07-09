package gift;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import gift.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import gift.model.Member;

import gift.service.MemberService;
import gift.util.JwtUtil;

@SpringBootTest
public class MemberServiceTest {

  @Mock
  private MemberRepository memberRepositoryMock;

  @Mock
  private JwtUtil jwtUtilMock;

  private MemberService memberService;

  @BeforeEach
  public void setUp() {
    memberService = new MemberService(memberRepositoryMock, jwtUtilMock);
  }

  @Test
  public void testRegister() {
    Member member = new Member();
    member.setEmail("test@example.com");
    member.setPassword("password");
    Long expectedMemberId = 1L;
    String expectedToken = "generated_token";
    Member savedMember = new Member();
    savedMember.setId(expectedMemberId);
    savedMember.setEmail(member.getEmail());
    savedMember.setPassword(member.getPassword());

    when(memberRepositoryMock.save(member)).thenReturn(savedMember);
    when(jwtUtilMock.generateToken(expectedMemberId, member.getEmail())).thenReturn(expectedToken);
    String token = memberService.register(member);

    assertEquals(expectedToken, token, "Generated token should match expected token");
  }

  @Test
  public void testLoginValidCredentials() {
    String email = "test@example.com";
    String password = "password";
    Long memberId = 1L;
    String expectedToken = "generated_token";

    Member member = new Member();
    member.setId(memberId);
    member.setEmail(email);
    member.setPassword(password);

    when(memberRepositoryMock.findByEmail(email)).thenReturn(java.util.Optional.of(member));
    when(jwtUtilMock.generateToken(memberId, email)).thenReturn(expectedToken);

    String token = memberService.login(email, password);

    assertEquals(expectedToken, token, "Generated token should match expected token");
  }

  @Test
  public void testLoginInvalidCredentials() {
    String email = "test@example.com";
    String wrongPassword = "Wrong_password";
    Long memberId = 1L;

    Member member = new Member();
    member.setId(memberId);
    member.setEmail(email);
    member.setPassword(wrongPassword);

    when(memberRepositoryMock.findByEmail(email)).thenReturn(java.util.Optional.of(member));

    assertThrows(IllegalArgumentException.class, () -> memberService.login(email, wrongPassword));
  }
}
