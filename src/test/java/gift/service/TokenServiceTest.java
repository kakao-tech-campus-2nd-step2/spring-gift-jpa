package gift.service;

import gift.domain.AuthToken;
import gift.exception.EmailDuplicationException;
import gift.exception.UnAuthorizationException;
import gift.repository.token.TokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;

    @Test
    @DisplayName("토큰 저장 테스트")
    void 토큰_저장_테스트(){
        //given
        String token = UUID.randomUUID().toString();
        String email = "abc@pusan.ac.kr";
        String duplicationEmail = "abcd@pusan.ac.kr";

        given(tokenRepository.findTokenByEmail(email)).willReturn(Optional.empty());
        given(tokenRepository.findTokenByEmail(duplicationEmail)).willReturn(Optional.of(new AuthToken(token, duplicationEmail)));

        //when
        String savedToken = tokenService.tokenSave(token, email);

        //then
        assertAll(
                () -> assertThat(savedToken).isEqualTo(token),
                () -> verify(tokenRepository, times(1)).save(any(AuthToken.class)),
                () -> assertThatThrownBy(() -> tokenService.tokenSave(token, duplicationEmail))
                        .isInstanceOf(EmailDuplicationException.class)
        );

    }

    @Test
    @DisplayName("토큰을 이용한 토큰 조회 테스트")
    void 토큰_토큰_조회_테스트(){
        //given
        String token = UUID.randomUUID().toString();
        String nullToken = UUID.randomUUID().toString();
        String email = "abc@pusan.ac.kr";

        given(tokenRepository.findAuthTokenByToken(token)).willReturn(Optional.of(new AuthToken(token, email)));
        given(tokenRepository.findAuthTokenByToken(nullToken)).willReturn(Optional.empty());

        //when
        AuthToken findAuthToken = tokenService.findToken(token);

        //then
        assertAll(
                () -> assertThat(findAuthToken.getToken()).isEqualTo(token),
                () -> assertThat(findAuthToken.getEmail()).isEqualTo(email),
                () -> assertThatThrownBy(() -> tokenService.findToken(nullToken))
                        .isInstanceOf(UnAuthorizationException.class)
        );


    }

}