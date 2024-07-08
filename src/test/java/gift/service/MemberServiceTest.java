package gift.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.dto.member.MemberRequest;
import gift.dto.member.MemberResponse;
import gift.exception.member.EmailAlreadyUsedException;
import gift.exception.member.ForbiddenException;
import gift.model.Member;
import gift.repository.MemberRepository;
import gift.util.JWTUtil;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberService memberService;
    private JWTUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        memberRepository = Mockito.mock(MemberRepository.class);
        jwtUtil = Mockito.mock(JWTUtil.class);  // JWTUtil을 목(mock) 객체로 초기화
        memberService = new MemberService(memberRepository, jwtUtil);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void testRegisterMember() {
        MemberRequest memberDTO = new MemberRequest(null, "test@example.com", "password");
        Member savedMember = new Member(1L, "test@example.com", "password");
        when(memberRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(memberRepository.create(any(Member.class))).thenReturn(savedMember);
        when(jwtUtil.generateToken(1L, "test@example.com")).thenReturn("mockedToken");

        MemberResponse response = memberService.registerMember(memberDTO);
        assertEquals("test@example.com", response.email());
        assertNotNull(response.token());
    }

    @Test
    @DisplayName("이미 사용 중인 이메일로 회원가입 시도")
    public void testRegisterMemberEmailAlreadyUsed() {
        MemberRequest memberDTO = new MemberRequest(null, "test@example.com", "password");
        when(memberRepository.existsByEmail("test@example.com")).thenReturn(true);

        EmailAlreadyUsedException exception = assertThrows(EmailAlreadyUsedException.class, () -> {
            memberService.registerMember(memberDTO);
        });

        assertEquals("이미 사용 중인 이메일입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("로그인 테스트")
    public void testLoginMember() {
        MemberRequest memberDTO = new MemberRequest(null, "test@example.com", "password");
        Member member = new Member(1L, "test@example.com", "password");
        when(memberRepository.findByEmail("test@example.com")).thenReturn(Optional.of(member));
        when(jwtUtil.generateToken(1L, "test@example.com")).thenReturn("mockedToken");

        MemberResponse response = memberService.loginMember(memberDTO);
        assertEquals("test@example.com", response.email());
        assertNotNull(response.token());
    }

    @Test
    @DisplayName("잘못된 이메일로 로그인 시도")
    public void testLoginMemberEmailNotFound() {
        MemberRequest memberDTO = new MemberRequest(null, "test@example.com", "password");
        when(memberRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            memberService.loginMember(memberDTO);
        });

        assertEquals("존재하지 않는 이메일입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 시도")
    public void testLoginMemberPasswordMismatch() {
        MemberRequest memberDTO = new MemberRequest(null, "test@example.com", "wrongpassword");
        Member member = new Member(1L, "test@example.com", "password");
        when(memberRepository.findByEmail("test@example.com")).thenReturn(Optional.of(member));

        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            memberService.loginMember(memberDTO);
        });

        assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("모든 회원 조회")
    public void testGetAllMembers() {
        Member member = new Member(1L, "test@example.com", "password");
        when(memberRepository.findAll()).thenReturn(List.of(member));

        List<MemberResponse> members = memberService.getAllMembers();
        assertEquals(1, members.size());
        assertEquals("test@example.com", members.get(0).email());
    }

    @Test
    @DisplayName("ID로 회원 조회")
    public void testGetMemberById() {
        Member member = new Member(1L, "test@example.com", "password");
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        MemberResponse memberDTO = memberService.getMemberById(1L);
        assertEquals("test@example.com", memberDTO.email());
    }

    @Test
    @DisplayName("존재하지 않는 ID로 회원 조회")
    public void testGetMemberByIdNotFound() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            memberService.getMemberById(1L);
        });

        assertEquals("존재하지 않는 이메일입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원 수정")
    public void testUpdateMember() {
        Member member = new Member(1L, "old@example.com", "oldpassword");
        MemberRequest memberDTO = new MemberRequest(1L, "new@example.com", "newpassword");
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(memberRepository.update(any(Member.class))).thenReturn(new Member(1L, "new@example.com", "newpassword"));

        MemberResponse response = memberService.updateMember(1L, memberDTO);
        assertEquals("new@example.com", response.email());
    }

    @Test
    @DisplayName("회원 삭제")
    public void testDeleteMember() {
        when(memberRepository.existsById(1L)).thenReturn(true);
        doNothing().when(memberRepository).delete(1L);

        memberService.deleteMember(1L);
        verify(memberRepository, times(1)).delete(1L);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 회원 삭제")
    public void testDeleteMemberNotFound() {
        when(memberRepository.existsById(1L)).thenReturn(false);

        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            memberService.deleteMember(1L);
        });

        assertEquals("존재하지 않는 ID입니다.", exception.getMessage());
    }
}
