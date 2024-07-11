package gift.repository;

import gift.common.enums.Role;
import gift.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByEmailAndPassword() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        save(email, password, role);

        // when
        Member actual = memberRepository.findByEmailAndPassword(email, password).orElse(null);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.getRole()).isEqualTo(role);
    }

    @Test
    void existsByEmail() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        save(email, password, role);

        // when
        boolean actual = memberRepository.existsByEmail(email);

        // then
        assertThat(actual).isTrue();
    }

    void save(String email, String password, Role role) {
        // given
        Member member = new Member(email, password, role);

        // when
        Member actual = memberRepository.save(member);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getRole()).isEqualTo(role);
    }

}