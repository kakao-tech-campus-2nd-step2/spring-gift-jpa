package gift.service;

import gift.dto.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenServiceTest {

    private static final Long MEMBER_ID = 1L;

    @Autowired
    private TokenService tokenService;

    @Test
    @DisplayName("멤버ID로 토큰 생성")
    void generateToken() {
        //When
        TokenResponse token = tokenService.generateToken(MEMBER_ID);

        //Then
        assertThat(token).isInstanceOf(TokenResponse.class);
    }

    @Test
    @DisplayName("토큰에서 멤버ID추출")
    void getMemberIdByToken() {
        //Given
        TokenResponse tokenResponse = tokenService.generateToken(MEMBER_ID);

        //When
        Long memberIdByToken = tokenService.getMemberIdByToken(tokenResponse.token());

        //Then
        assertThat(memberIdByToken).isEqualTo(MEMBER_ID);
    }

    @Test
    @DisplayName("토큰 유효성 검증")
    void isValidateToken() {
        //Given
        TokenResponse tokenResponse = tokenService.generateToken(MEMBER_ID);

        //When
        boolean isValidate = tokenService.isValidateToken(tokenResponse.token());

        //Then
        assertThat(isValidate).isTrue();
    }

}
