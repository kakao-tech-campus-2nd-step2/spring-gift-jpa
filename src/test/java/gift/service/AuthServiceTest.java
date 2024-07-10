package gift.service;

import gift.domain.Member;
import gift.dto.request.MemberRequestDto;
import gift.dto.response.MemberResponseDto;
import gift.exception.EmailDuplicationException;
import gift.exception.MemberNotFoundException;
import gift.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 정상 테스트")
    void 회원_가입_정상_테스트(){
        //given
        Member member = new Member.Builder()
                .email("abc@pusan.ac.kr")
                .password("abc")
                .build();

        MemberRequestDto memberRequestDto = new MemberRequestDto(member.getEmail(), member.getPassword());

        given(memberRepository.findMemberByEmail(memberRequestDto.email())).willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willReturn(member);

        //when
        MemberResponseDto returnMember = authService.memberJoin(memberRequestDto);

        //then
        assertAll(
                () -> assertThat(returnMember.email()).isEqualTo(member.getEmail()),
                () -> assertThat(returnMember.password()).isEqualTo(member.getPassword())
        );
    }

    @Test
    @DisplayName("회원 가입 실패 테스트")
    void 회원_가입_실패_테스트(){
        //given
        Member member = new Member.Builder()
                .email("abc@pusan.ac.kr")
                .password("abc")
                .build();

        MemberRequestDto memberRequestDto = new MemberRequestDto(member.getEmail(), member.getPassword());
        given(memberRepository.findMemberByEmail(memberRequestDto.email())).willReturn(Optional.of(member));

        //when then
        assertThatThrownBy(() -> authService.memberJoin(memberRequestDto))
                .isInstanceOf(EmailDuplicationException.class);
    }

    @Test
    @DisplayName("이메일과 패스워드로 조회 테스트")
    void 이메일_패스워드_조회_테스트(){
        //given
        MemberRequestDto memberRequestDto = new MemberRequestDto("테스트@pusan.ac.kr", "abc");

        Member member = new Member.Builder()
                .email(memberRequestDto.email())
                .password(memberRequestDto.password())
                .build();

        given(memberRepository.findMemberByEmailAndPassword(memberRequestDto.email(), memberRequestDto.password()))
                .willReturn(Optional.of(member));

        MemberRequestDto memberFailDto = new MemberRequestDto("테스트2@pusan.ac.kr", "abc");

        given(memberRepository.findMemberByEmailAndPassword(memberFailDto.email(), memberFailDto.password()))
                .willReturn(Optional.empty());

        //when
        MemberResponseDto findMemberDto = authService.findOneByEmailAndPassword(memberRequestDto);

        //then
        assertAll(
                () -> assertThat(findMemberDto.email()).isEqualTo(member.getEmail()),
                () -> assertThat(findMemberDto.password()).isEqualTo(member.getPassword())
        );

        assertThatThrownBy(() -> authService.findOneByEmailAndPassword(memberFailDto))
                .isInstanceOf(MemberNotFoundException.class);
    }
}