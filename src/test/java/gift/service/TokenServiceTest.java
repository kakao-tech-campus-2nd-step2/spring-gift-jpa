package gift.service;

import gift.dto.Member;
import gift.dto.response.TokenResponse;
import org.junit.jupiter.api.Assertions;
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
    void generateToken() {
        //When
        TokenResponse token = tokenService.generateToken(MEMBER_ID);

        //Then
        assertThat(token).isInstanceOf(TokenResponse.class);
    }

    @Test
    void getMemberIdByToken() {
        //Given
        TokenResponse token = tokenService.generateToken(MEMBER_ID);

        //When
        Long memberIdByToken = tokenService.getMemberIdByToken(token.getToken());

        //Then
        assertThat(memberIdByToken).isEqualTo(MEMBER_ID);
    }

    @Test
    void isValidateToken() {
        //Given
        TokenResponse token = tokenService.generateToken(MEMBER_ID);

        //When
        boolean isValidate = tokenService.isValidateToken(token.getToken());

        //Then
        assertThat(isValidate).isTrue();
    }

}
