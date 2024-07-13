package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.domain.member.dto.MemberRequest;
import gift.domain.member.entity.Member;
import gift.domain.member.service.MemberService;
import gift.domain.member.repository.MemberRepository;
import gift.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("회원가입 테스트")
    void register() {
        //given
        MemberRequest memberRequest = new MemberRequest("test@email.com", "password");
        Member savedMember = new Member(memberRequest.getEmail(), memberRequest.getPassword());

        doReturn(savedMember).when(memberRepository).save(any(Member.class));
        doReturn("jwtToken").when(jwtUtil).generateToken(any(Member.class));

        // when
        String token = memberService.register(memberRequest);

        // then
        assertThat(token).isEqualTo("jwtToken");
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() {
        // given
        MemberRequest memberRequest = new MemberRequest("test@google.co.kr", "password");
        Member savedMember = new Member(memberRequest.getEmail(), memberRequest.getPassword());

        doReturn(Optional.of(savedMember)).when(memberRepository).findByEmail(any(String.class));
        doReturn("jwtToken").when(jwtUtil).generateToken(any(Member.class));

        // when
        String responseToken = memberService.login(memberRequest);

        // then
        assertThat(responseToken).isEqualTo("jwtToken");
    }

    @Test
    @DisplayName("비밀번호 불일치 시 로그인 실패 테스트")
    void loginFail() {
        // given
        MemberRequest memberRequest = new MemberRequest("test@google.co.kr", "wrongPassword");
        Member savedMember = new Member(memberRequest.getEmail(), "password");

        doReturn(Optional.of(savedMember)).when(memberRepository).findByEmail(any(String.class));

        // when
        String responseToken = memberService.login(memberRequest);

        // then
        assertThat(responseToken).isNull();
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found Entity"));
        memberRepository.delete(member);
    }

    @Test
    @DisplayName("멤버 삭제 테스트")
    void deleteMember() {
        // given
        Long id = 1L;
        Member savedMember = new Member(1L, "test@gmail.co.kr", "password");

        // when
        doReturn(Optional.of(savedMember)).when(memberRepository).findById(id);
        memberService.deleteMember(id);
        // then
        verify(memberRepository, times(1)).delete(savedMember);
    }

    @Test
    @DisplayName("토큰으로 저장된 멤버 가져오기 테스트")
    void getMemberFromToken() {
        // given
        String RequestToken = "jwtToken";
        Member savedMember = new Member("test@google.co.kr", "password");

        doReturn("email").when(jwtUtil).getEmailFromToken(RequestToken);
        doReturn(Optional.of(savedMember)).when(memberRepository).findByEmail("email");

        // when
        Member responseMember = memberService.getMemberFromToken(RequestToken);

        // then
        assertThat(responseMember.getEmail()).isEqualTo(savedMember.getEmail());
    }
}
