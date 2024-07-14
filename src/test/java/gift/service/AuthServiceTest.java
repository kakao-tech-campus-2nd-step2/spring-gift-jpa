package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gift.product.dto.JwtResponse;
import gift.product.dto.MemberDto;
import gift.product.exception.LoginFailedException;
import gift.product.service.AuthService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AuthServiceTest {

    final AuthService authService;
    final MemberDto memberDto = new MemberDto("test@test.com", "1234");

    @Autowired
    AuthServiceTest(AuthService authService) {
        this.authService = authService;
    }

    @Test
    void 회원가입_및_로그인() {
        authService.register(memberDto);
        JwtResponse jwtResponse = authService.login(memberDto);

        assertThat(jwtResponse.token()).isNotEmpty();
    }

    @Test
    void 회원가입_중복() {
        MemberDto testAcount = new MemberDto("test@test.com", "test");
        authService.register(testAcount);

        assertThatThrownBy(() -> authService.register(testAcount)).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_회원_로그인() {
        MemberDto testAcount = new MemberDto("test@test.com", "test");

        assertThatThrownBy(() -> authService.login(testAcount)).isInstanceOf(
            LoginFailedException.class);
    }
}
