package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.model.Member;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private String email = "test@email.com";
    private String password = "mypassword";

    @BeforeEach
    void setUp() {
        memberRepository.save(new Member(email, password));
    }

    @Test
    void save() {
        var expected = new Member(email, password);
        assertThat(memberRepository.findAll()).isNotNull();
        assertAll(
            () -> assertThat(memberRepository.findAll().getFirst().getEmail()).isEqualTo(
                expected.getEmail()),
            () -> assertThat(memberRepository.findAll().getFirst().getPassword()).isEqualTo(
                expected.getPassword())
        );
    }

    @Test
    void findByEmail() {

        var actual = memberRepository.findByEmail(email);
        assertAll(
            () -> {
                assertTrue(actual.isPresent());
                assertThat(actual.get().getEmail()).isEqualTo(email);
                assertThat(actual.get().getPassword()).isEqualTo(password);
            }
        );
    }

    @Test
    void deleteTest() {
        memberRepository.delete(memberRepository.findAll().getFirst());
        assertThat(memberRepository.findAll().isEmpty()).isTrue();
    }
}
