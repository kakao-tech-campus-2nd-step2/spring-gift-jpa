package gift.member.service;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMember() {
        // Given
        String email = "test@example.com";
        String password = "password";
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        Member mockMember = new Member(email, "encodedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(mockMember);

        // When
        Member createdMember = memberService.createMember(email, password);

        // Then
        assertThat(createdMember.getEmail()).isEqualTo(email);
        assertThat(createdMember.getPassword()).isEqualTo("encodedPassword");
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void testRegisterNewMember() {
        // Given
        String email = "new@example.com";
        String password = "newPassword";
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());
        Member mockMember = new Member(email, "encodedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(mockMember);

        // When
        Member registeredMember = memberService.register(email, password);

        // Then
        assertThat(registeredMember.getEmail()).isEqualTo(email);
        assertThat(registeredMember.getPassword()).isEqualTo("encodedPassword");
        verify(memberRepository, times(1)).findByEmail(email);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void testLoginValidCredentials() {
        // Given
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        Member mockMember = new Member(email, encodedPassword);
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(mockMember));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        // When
        Member loggedInMember = memberService.login(email, password);

        // Then
        assertThat(loggedInMember.getEmail()).isEqualTo(email);
        assertThat(loggedInMember.getPassword()).isEqualTo(encodedPassword);
    }

    @Test
    public void testUpdateEmail() {
        // Given
        Long memberId = 1L;
        String newEmail = "newemail@example.com";
        Member existingMember = new Member("oldemail@example.com", "password");
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));
        Member updatedMember = new Member(newEmail, "password");
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        // When
        Member result = memberService.updateEmail(memberId, newEmail);

        // Then
        assertThat(result.getEmail()).isEqualTo(newEmail);
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void testUpdatePassword() {
        // Given
        Long memberId = 1L;
        String newPassword = "newPassword";
        Member existingMember = new Member("test@example.com", "password");
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));
        Member updatedMember = new Member("test@example.com", "encodedNewPassword");
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        // When
        Member result = memberService.updatePassword(memberId, newPassword);

        // Then
        assertThat(result.getPassword()).isEqualTo("encodedNewPassword");
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void testDeleteMember() {
        // Given
        Long memberId = 1L;

        // When
        memberService.deleteMember(memberId);

        // Then
        verify(memberRepository, times(1)).deleteById(memberId);
    }
}