package gift;

import gift.domain.Member;
import gift.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testSaveMember() {
        Member member = new Member(null, "test@example.com", "password");
        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        Member member = memberRepository.findByEmail(email);

        assertThat(member).isNotNull();
        assertThat(member.getEmail()).isEqualTo(email);
    }
}
