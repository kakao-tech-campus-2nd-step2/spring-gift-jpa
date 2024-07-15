package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import gift.product.model.Member;
import gift.product.repository.AuthRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AuthRepositoryTest {

    @Autowired
    AuthRepository authRepository;

    @Test
    void 회원가입() {
        Member member = new Member("test@test.com", "test");
        Member registeredMember = authRepository.save(member);

        assertSoftly(softly -> {
            assertThat(registeredMember.getId()).isNotNull();
            assertThat(registeredMember.getEmail()).isEqualTo("test@test.com");
            assertThat(registeredMember.getPassword()).isEqualTo("test");
        });
    }

    @Test
    void 이메일로_회원_찾기() {
        Member member = new Member("test@test.com", "test");
        Member registeredMember = authRepository.save(member);

        Member findedMember = authRepository.findByEmail(registeredMember.getEmail());

        assertThat(findedMember).isNotNull();
    }

    @Test
    void 특정_이메일을_가진_회원_존재_여부() {
        Member member = new Member("test@test.com", "test");
        Member registeredMember = authRepository.save(member);

        boolean isMemberPresent = authRepository.existsByEmail(registeredMember.getEmail());
        assertThat(isMemberPresent).isTrue();
    }

    @Test
    void 특정_ID를_가진_회원_존재_여부() {
        Member member = new Member("test@test.com", "test");
        Member registeredMember = authRepository.save(member);

        boolean isMemberPresent = authRepository.existsById(registeredMember.getId());
        assertThat(isMemberPresent).isTrue();
    }
}
