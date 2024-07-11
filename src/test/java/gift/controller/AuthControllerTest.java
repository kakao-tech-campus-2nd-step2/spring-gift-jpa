package gift.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.configuration.FilterConfiguration;
import gift.domain.AuthToken;
import gift.dto.request.MemberRequestDto;
import gift.dto.response.MemberResponseDto;
import gift.repository.token.TokenRepository;
import gift.service.AuthService;
import gift.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(FilterConfiguration.class)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AuthService authService;

    @MockBean
    TokenService tokenService;

    @MockBean
    TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("회원가입 테스트")
    void 회원_가입_테스트() throws Exception {
        //given
        MemberRequestDto memberRequestDto = new MemberRequestDto("test@pusan.ac.kr", "p");
        MemberRequestDto inValidMemberRequestDto = new MemberRequestDto("test", "p");

        //when then
        mvc.perform(post("/members/register")
                        .content(objectMapper.writeValueAsString(memberRequestDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andDo(print());

        mvc.perform(post("/members/register")
                        .content(objectMapper.writeValueAsString(inValidMemberRequestDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").value("이메일 형식을 맞춰 주세요"))
                .andDo(print());

    }

    @Test
    @DisplayName("회원 처음 로그인 테스트")
    void 회원_처음_로그인_테스트() throws Exception{
        //given
        MemberRequestDto memberRequestDto = new MemberRequestDto("test@pusan.ac.kr", "password");
        MemberRequestDto inValidMemberRequestDto = new MemberRequestDto("test", "password");

        MemberResponseDto memberResponseDto = new MemberResponseDto(1L, "test@pusan.ac.kr", "password");
        given(authService.findOneByEmailAndPassword(memberRequestDto)).willReturn(memberResponseDto);

        //when then
        mvc.perform(post("/members/login")
                        .content(objectMapper.writeValueAsString(memberRequestDto))
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andDo(print());

        mvc.perform(post("/members/login")
                        .content(objectMapper.writeValueAsString(inValidMemberRequestDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").value("이메일 형식을 맞춰 주세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 중복 시도 필터 통과 실패 테스트")
    void 로그인_중복_시도_필터_통과_실패_테스트() throws Exception{
        //given
        AuthToken authToken = new AuthToken("테스트 인증 정보", "test@pusan.ac.kr");
        given(tokenRepository.findAuthTokenByToken("테스트 인증 정보")).willReturn(Optional.of(authToken));

        //when then
        mvc.perform(post("/members/login")
                        .header("Authorization","Bearer 테스트 인증 정보")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
                .andDo(print());
    }

}