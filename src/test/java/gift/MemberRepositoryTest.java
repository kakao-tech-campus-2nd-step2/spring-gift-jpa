package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Member;
import gift.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository members;

    @Test
    void save() {
        Member expected = new Member("test@test.com", "password");
        Member actual = members.save(expected);
        assertThat(members.findAll()).isNotEmpty();
    }

    @Test
    void find() {
        Member expected = new Member("test@test.com", "password");
        members.save(expected);
        assertThat(members.findByEmailAndPassword(expected.getEmail(),
            expected.getPassword())).isNotNull();
    }
}
