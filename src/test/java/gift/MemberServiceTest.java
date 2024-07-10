package gift;

import gift.model.Member;
import gift.repository.MemberRepository;
import gift.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // 회원가입
        // 이메일과 비밀번호를 통해 회원을 등록하고 저장된 회원 정보가 올바른지 확인
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(memberRepository.findByEmail(email)).thenReturn(null);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Member member = memberService.register(email, password);

        assertThat(member).isNotNull();
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getPassword()).isEqualTo(encodedPassword);
    }

    @Test
    void testRegisterThrowsExceptionWhenEmailExists() {
        //중복 이메일 회원가입
        String email = "test@example.com";
        String password = "password";

        when(memberRepository.findByEmail(email)).thenReturn(new Member());

        assertThrows(IllegalArgumentException.class, () -> memberService.register(email, password));
    }

    @Test
    void testAuthenticate() {
        //로그인
        //이메일과 비밀번호를 통해 로그인을 시도하고 올바른 회원 정보가 반환되는지 확인
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        when(memberRepository.findByEmail(email)).thenReturn(member);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        Member authenticatedMember = memberService.authenticate(email, password);

        assertThat(authenticatedMember).isNotNull();
        assertThat(authenticatedMember.getEmail()).isEqualTo(email);
    }

    @Test
    void testAuthenticateReturnsNullWhenPasswordDoesNotMatch() {
        // 비밀번호 일치하지 않을 때 로그인 실패하는지 확인
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        when(memberRepository.findByEmail(email)).thenReturn(member);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        Member authenticatedMember = memberService.authenticate(email, password);

        assertThat(authenticatedMember).isNull();
    }

    @Test
    void testFindById() {
        //ID로 회원 조회
        Long id = 1L;
        Member member = Member.builder()
                .id(id)
                .email("test@example.com")
                .password("password")
                .build();

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        Member foundMember = memberService.findById(id);

        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getId()).isEqualTo(id);
    }

    @Test
    void testFindByIdReturnsNullWhenNotFound() {
        //없는 ID로 회원 조회시 Null 반환
        Long id = 1L;

        when(memberRepository.findById(id)).thenReturn(Optional.empty());

        Member foundMember = memberService.findById(id);

        assertThat(foundMember).isNull();
    }
}