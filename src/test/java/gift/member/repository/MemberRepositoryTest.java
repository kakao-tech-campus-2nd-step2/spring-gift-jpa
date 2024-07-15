package gift.member.repository;

import gift.member.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveTest() {
        // Given
        Member member = new Member("test@example.com", "password");

        // When
        member = memberRepository.save(member);

        // Then
        Optional<Member> foundMember = memberRepository.findById(member.member_id());
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().email()).isEqualTo("test@example.com");
    }

    @Test
    public void findByEmailTest() {
        // Given
        Member member = new Member("test@example.com", "password");
        memberRepository.save(member);

        // When
        Optional<Member> foundMember = memberRepository.findByEmail("test@example.com");

        // Then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().email()).isEqualTo("test@example.com");
    }

    @Test
    public void deleteTest() {
        // Given
        Member member = new Member("test@example.com", "password");
        member = memberRepository.save(member);

        // When
        memberRepository.deleteById(member.member_id());

        // Then
        Optional<Member> deletedMember = memberRepository.findById(member.member_id());
        assertThat(deletedMember).isEmpty();
    }
}