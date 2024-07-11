package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.domain.Member;
import gift.dto.MemberRequest;
import gift.entity.MemberEntity;
import gift.repository.MemberRepository;
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
        MemberEntity savedMember = new MemberEntity(memberRequest.getEmail(), memberRequest.getPassword());

        doReturn(savedMember).when(memberRepository).save(any(MemberEntity.class));
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
        MemberEntity savedMember = new MemberEntity(memberRequest.getEmail(), memberRequest.getPassword());

        doReturn(Optional.of(savedMember)).when(memberRepository).findByEmail(any(String.class));
        doReturn("jwtToken").when(jwtUtil).generateToken(any(Member.class));

        // when
        String responseToken = memberService.login(memberRequest);

        // then
        assertThat(responseToken).isEqualTo("jwtToken");
    }

    @Test
    @DisplayName("비밀번호 불일치 시 로그인 실패 테스트")
    void loginFail(){
        // given
        MemberRequest memberRequest = new MemberRequest("test@google.co.kr", "wrongPassword");
        MemberEntity savedMember = new MemberEntity(memberRequest.getEmail(), "password");

        doReturn(Optional.of(savedMember)).when(memberRepository).findByEmail(any(String.class));

        // when
        String responseToken = memberService.login(memberRequest);

        // then
        assertThat(responseToken).isNull();
    }

    public void deleteMember(Long id){
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found Entity"));
        memberRepository.delete(memberEntity);
    }
    @Test
    @DisplayName("멤버 삭제 테스트")
    void delete(){
        // given
        Long id = 1L;
        MemberEntity savedMember = new MemberEntity("test@gmail.co.kr", "password");

        // when
        doReturn(Optional.of(savedMember)).when(memberRepository).findById(id);

        // then4
        verify(memberRepository, times(1)).delete(savedMember);
    }

    @Test
    @DisplayName("토큰으로 저장된 멤버 가져오기 테스트")
    void getMemberFromToken(){
        // given
        String RequestToken = "jwtToken";
        MemberEntity savedMember = new MemberEntity("test@google.co.kr", "password");

        doReturn("email").when(jwtUtil).getEmailFromToken(RequestToken);
        doReturn(Optional.of(savedMember)).when(memberRepository).findByEmail("email");

        // when
        Member responseMember = memberService.getMemberFromToken(RequestToken);

        // then
        assertThat(responseMember.getEmail()).isEqualTo(savedMember.getEmail());
    }
}
