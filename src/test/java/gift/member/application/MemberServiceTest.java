package gift.member.application;

import gift.exception.type.NotFoundException;
import gift.member.application.command.MemberJoinCommand;
import gift.member.application.command.MemberLoginCommand;
import gift.member.application.command.MemberUpdateCommand;
import gift.member.domain.Member;
import gift.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = new Member("test@example.com", "password");
    }

    @Test
    void 회원가입_테스트() {
        // Given
        MemberJoinCommand command = new MemberJoinCommand("test@example.com", "password");
        when(memberRepository.join(any())).thenReturn(member.getEmail());

        // When
        String email = memberService.join(command);

        // Then
        assertEquals(member.getEmail(), email);
        verify(memberRepository, times(1)).join(any());
    }

    @Test
    void 로그인_테스트() {
        // Given
        MemberLoginCommand command = new MemberLoginCommand("test@example.com", "password");
        when(memberRepository.login(any())).thenReturn(member.getEmail());

        // When
        String email = memberService.login(command);

        // Then
        assertEquals(member.getEmail(), email);
        verify(memberRepository, times(1)).login(any());
    }

    @Test
    void 회원_업데이트_테스트() {
        // Given
        MemberUpdateCommand command = new MemberUpdateCommand("test@example.com", "newPassword");
        when(memberRepository.findByEmail(command.email())).thenReturn(Optional.of(member));

        // When
        assertDoesNotThrow(() -> memberService.update(command));

        // Then
        verify(memberRepository, times(1)).update(any());
    }

    @Test
    void 회원_업데이트_테스트_회원_없음() {
        // Given
        MemberUpdateCommand command = new MemberUpdateCommand("test@example.com", "newPassword");
        when(memberRepository.findByEmail(command.email())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> memberService.update(command));
    }

    @Test
    void 이메일로_회원_찾기_테스트() {
        // Given
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));

        // When
        MemberResponse response = memberService.findByEmail(member.getEmail());

        // Then
        assertEquals(member.getEmail(), response.email());
        verify(memberRepository, times(1)).findByEmail(member.getEmail());
    }

    @Test
    void 이메일로_회원_찾기_테스트_회원_없음() {
        // Given
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> memberService.findByEmail(member.getEmail()));
    }

    @Test
    void 전체_회원_찾기_테스트() {
        // Given
        Member member2 = new Member("test2@example.com", "password2");
        when(memberRepository.findAll()).thenReturn(Arrays.asList(member, member2));

        // When
        List<MemberResponse> responses = memberService.findAll();

        // Then
        assertEquals(2, responses.size());
        assertEquals(member.getEmail(), responses.get(0).email());
        assertEquals(member2.getEmail(), responses.get(1).email());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void 회원_삭제_테스트() {
        // Given
        doNothing().when(memberRepository).delete(member.getEmail());

        // When
        assertDoesNotThrow(() -> memberService.delete(member.getEmail()));

        // Then
        verify(memberRepository, times(1)).delete(member.getEmail());
    }
}
