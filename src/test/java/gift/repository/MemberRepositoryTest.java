package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByEmail() {
        Member expected = new Member("member1@example.com", "password1", "member1", "user");
        memberRepository.save(expected);

        Member actual = memberRepository.findByEmail(expected.getEmail());

        assertThat(actual).isEqualTo(expected);
    }
}
