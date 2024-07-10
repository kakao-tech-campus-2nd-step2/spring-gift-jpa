package gift.service;

import gift.dto.request.MemberRequest;
import gift.domain.Member;
import gift.exception.DuplicateMemberException;
import gift.exception.InvalidCredentialsException;
import gift.exception.MemberNotFoundException;
import gift.repository.member.MemberSpringDataJpaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberSpringDataJpaRepository memberRepository;

    @Test
    public void testRegister() {
        MemberRequest memberRequest = new MemberRequest("test@example.com", "password");

        given(memberRepository.findByEmail("test@example.com")).willReturn(Optional.empty());

        Member member = memberService.register(memberRequest);

        assertThat(member.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testDuplicateRegister() {
        MemberRequest memberRequest = new MemberRequest("test@example.com", "password");
        given(memberRepository.findByEmail("test@example.com")).willReturn(Optional.of(new Member()));

        assertThrows(DuplicateMemberException.class, () -> {
            memberService.register(memberRequest);
        });
    }

    @Test
    public void testAuthenticate() {
        MemberRequest memberRequest = new MemberRequest("test@example.com", "password");
        Member member = new Member("test@example.com", "password");

        given(memberRepository.findByEmail("test@example.com")).willReturn(Optional.of(member));

        Member authenticatedMember = memberService.authenticate(memberRequest);

        assertThat(authenticatedMember.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testAuthenticateWithInvalidCredentials() {
        MemberRequest memberRequest = new MemberRequest("test@example.com", "wrongpassword");
        Member member = new Member("test@example.com", "password");

        given(memberRepository.findByEmail("test@example.com")).willReturn(Optional.of(member));

        assertThrows(InvalidCredentialsException.class, () -> {
            memberService.authenticate(memberRequest);
        });
    }

    @Test
    public void testAuthenticateWithNonExistentEmail() {
        MemberRequest memberRequest = new MemberRequest("nonexistent@example.com", "password");

        given(memberRepository.findByEmail("nonexistent@example.com")).willReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> {
            memberService.authenticate(memberRequest);
        });
    }
}
