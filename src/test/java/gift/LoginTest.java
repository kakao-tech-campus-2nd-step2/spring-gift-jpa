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
@SuppressWarnings("NonAsciiCharacters")
class LoginTest {

    final AuthService authService;
    final MemberDto memberDto = new MemberDto("test@test.com", "1234");

    @Autowired
    LoginTest(AuthService authService) {
        this.authService = authService;
    }

    @Test
    void 회원가입_및_로그인_테스트() {
        authService.register(memberDto);
        JwtResponse jwtResponse = authService.login(memberDto);

        assertThat(jwtResponse.token()).isNotEmpty();
    }
}
