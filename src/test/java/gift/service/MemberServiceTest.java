package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import gift.domain.Member;
import gift.dto.MemberRequest;
import gift.repository.MemberRepository;
import gift.util.JwtUtil;
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
        Member savedMember = new Member(1L, memberRequest.getEmail(), memberRequest.getPassword());

        doReturn(savedMember).when(memberRepository).save(any(MemberRequest.class));
        doReturn("jwtToken").when(jwtUtil).generateToken(savedMember);

        // when
        String token = memberService.register(memberRequest);

        // then
        assertThat(token).isEqualTo("jwtToken");
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() {
        // given
        MemberRequest memberRequest = new MemberRequest("test@email.com", "password");
        Member savedMember = new Member( 1L, memberRequest.getEmail(), memberRequest.getPassword());

        doReturn(savedMember).when(memberRepository).findByEmail(memberRequest.getEmail());
        doReturn("jwtToken").when(jwtUtil).generateToken(savedMember);

        // when
        String responseToken = memberService.login(memberRequest);

        // then
        assertThat(responseToken).isEqualTo("jwtToken");
    }

    @Test
    @DisplayName("비밀번호 불일치 시 로그인 실패 테스트")
    void loginFail(){
        // given
        MemberRequest memberRequest = new MemberRequest("test@email.com", "wrongPassword");
        Member savedMember = new Member( 1L, memberRequest.getEmail(), "password");

        doReturn(savedMember).when(memberRepository).findByEmail(memberRequest.getEmail());

        // when
        String responseToken = memberService.login(memberRequest);

        // then
        assertThat(responseToken).isNull();
    }

    @Test
    @DisplayName("토큰으로 저장된 멤버 가져오기 테스트")
    void getMemberFromTokenTest(){
        // given
        String RequestToken = "jwtToken";
        Member savedMember = new Member(1L, "email", "password");

        doReturn("email").when(jwtUtil).getEmailFromToken(RequestToken);
        doReturn(savedMember).when(memberRepository).findByEmail("email");

        // when
        Member responseMember = memberService.getMemberFromToken(RequestToken);

        // then
        assertThat(responseMember.getEmail()).isEqualTo(savedMember.getEmail());
    }
}
