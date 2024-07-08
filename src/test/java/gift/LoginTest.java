package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.dto.JwtResponse;
import gift.product.dto.MemberDto;
import gift.product.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LoginTest {

    final AuthService authService;
    final MemberDto memberDto = new MemberDto("test@test.com", "1234");
    final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJfaWQiOjJ9.2dEFSG375_OkkZg6OfXIsfO5lRc42YBI-PF_SB0fo68";

    @Autowired
    LoginTest(AuthService authService) {
        this.authService = authService;
    }

    @Test
    void 회원가입_및_로그인_테스트() {
        authService.register(memberDto);
        JwtResponse jwtResponse = authService.login(memberDto);

        assertThat(jwtResponse.token()).isEqualTo(ACCESS_TOKEN);
    }
}
