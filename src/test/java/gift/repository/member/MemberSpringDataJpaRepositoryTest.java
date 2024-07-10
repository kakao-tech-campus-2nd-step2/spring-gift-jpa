package gift.repository.member;

import gift.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberSpringDataJpaRepositoryTest {

    @Autowired
    private MemberSpringDataJpaRepository memberRepository;

    @Test
    public void testSaveMember() {
        Member member = new Member("test@example.com", "password");
        memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findByEmail("test@example.com");
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testFindByEmail() {
        Member member = new Member("test@example.com", "password");
        memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findByEmail("test@example.com");
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo("test@example.com");
    }
}
