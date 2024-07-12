package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import gift.model.Member;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Test
    void save() {
        var expected = new Member("test@email.com", "mypassword");
        var actual = memberRepository.save(expected);
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    void findByEmail() {
        var expected = new Member("test@email.com", "mypassword");
        var savedMember = memberRepository.save(expected);

        var actual = memberRepository.findByEmail(savedMember.getEmail());
        assertAll(
            () -> {assertTrue(actual.isPresent());
            assertThat(actual.get()).isEqualTo(expected);}
        );
    }

    @Test
    void deleteTest(){
        var expected = new Member("test@email.com", "mypassword");
        var actual = memberRepository.save(expected);
        memberRepository.delete(actual);
        assertThat(memberRepository.findById(actual.getId())).isEqualTo(Optional.empty());
    }
}
