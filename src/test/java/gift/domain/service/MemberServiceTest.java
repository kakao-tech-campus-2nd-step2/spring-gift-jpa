package gift.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import gift.domain.dto.request.MemberRequest;
import gift.domain.dto.response.MemberResponse;
import gift.domain.entity.Member;
import gift.domain.exception.MemberAlreadyExistsException;
import gift.domain.exception.MemberIncorrectLoginInfoException;
import gift.domain.repository.MemberRepository;
import gift.global.util.HashUtil;
import gift.global.util.JwtUtil;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtUtil jwtUtil;

    private MemberRequest memberRequest;
    private Member member;
    private String createdToken;

    @BeforeEach
    void setUp() {
        createdToken = "token";
        memberRequest = new MemberRequest("test@example.com", "password");
        member = new Member("test@example.com", HashUtil.hashCode("password"), "user"); //id가 null이 들어가게 됨
        ReflectionTestUtils.setField(member, "id", 1L); //리플렉션 툴을 통해 아이디를 채운다.
    }

    @Test
    @DisplayName("회원가입 - 처음 가입하는 경우")
    void registerUser() {
        //given (mocking)
        given(memberRepository.findByEmail(memberRequest.email()))
            .willReturn(Optional.empty()); //기존 회원 확인하는 과정에서 Optional.empty() 반환(mock 설정)
        given(memberRepository.save(any(Member.class)))
            .willReturn(member); //저장된 회원 정보 반환(mock 설정)
        given(jwtUtil.generateToken(any(Member.class)))
            .willReturn(createdToken); //JWT 생성 과정(mock 설정)

        //when
        MemberResponse response = memberService.registerUser(memberRequest);

        //then
        assertThat(response).isNotNull();
        assertThat(response.token()).isEqualTo(createdToken);
        // memberRepository.save() 호출 검증
        then(memberRepository).should(times(1)).save(any(Member.class));
        // memberRepository.findByEmail() 호출 검증
        then(memberRepository).should(times(1)).findByEmail(eq(memberRequest.email()));
        // jwtUtil.generateToken() 호출 검증
        then(jwtUtil).should(times(1)).generateToken(any(Member.class));
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 회원가입 된 상황")
    void registerUser_AlreadyExistsMember() {
        //given: 이미 존재하는 회원(mock 설정)
        given(memberRepository.findByEmail(memberRequest.email())).willReturn(Optional.of(member));

        //when: 예외 발생 검증
        assertThatThrownBy(() -> memberService.registerUser(memberRequest))
            .isInstanceOf(MemberAlreadyExistsException.class);

        //then
        // memberRepository.save() 호출 검증하지 않음
        then(memberRepository).should(never()).save(any(Member.class));
        // jwtUtil.generateToken() 호출 검증하지 않음
        then(jwtUtil).should(never()).generateToken(any(Member.class));
    }

    @Test
    @DisplayName("로그인 - 아이디 존재하고 비밀번호 일치하는 경우")
    void loginUser() {
        //given
        given(memberRepository.findByEmail(memberRequest.email())).willReturn(Optional.of(member));
        given(jwtUtil.generateToken(any(Member.class))).willReturn(createdToken);

        //when
        MemberResponse response = memberService.loginUser(memberRequest);

        //then
        assertThat(response).isNotNull();
        assertThat(response.token()).isEqualTo(createdToken);
        then(memberRepository).should(times(1)).findByEmail(eq(memberRequest.email()));
        then(jwtUtil).should(times(1)).generateToken(eq(member));
    }

    @Test
    @DisplayName("로그인 실패 - 아이디가 존재하지 않은 경우")
    void loginUser_IdNotExists() {
        //given
        given(memberRepository.findByEmail(eq(memberRequest.email()))).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> memberService.loginUser(memberRequest))
            .isInstanceOf(MemberIncorrectLoginInfoException.class);

        then(jwtUtil).should(never()).generateToken(any(Member.class));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호가 일치하지 않은 경우")
    void loginUser_IncorrectPassword() {
        //given
        MemberRequest wrongPasswordRequest = new MemberRequest(memberRequest.email(), "incorrectPassword");
        given(memberRepository.findByEmail(eq(wrongPasswordRequest.email()))).willReturn(Optional.of(member));

        //when & then
        assertThatThrownBy(() -> memberService.loginUser(wrongPasswordRequest))
            .isInstanceOf(MemberIncorrectLoginInfoException.class);

        then(jwtUtil).should(never()).generateToken(any(Member.class));
    }

    @Test
    @DisplayName("로그인 실패 - 아이디 존재하지 않고 비밀번호가 일치하지 않은 경우")
    void loginUser_IdNotExistsAndIncorrectPassword() {
        //given
        MemberRequest incorrectRequest = new MemberRequest("notExist@example.com", "incorrectPassword");
        given(memberRepository.findByEmail(eq(incorrectRequest.email()))).willReturn(Optional.of(member));

        //when & then
        assertThatThrownBy(() -> memberService.loginUser(incorrectRequest))
            .isInstanceOf(MemberIncorrectLoginInfoException.class);

        then(jwtUtil).should(never()).generateToken(any(Member.class));
    }
}
