package gift.member.repository;

import gift.member.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testSaveMember() {
        // Given
        Member member = new Member("test@example.com", "password123");

        // When
        Member savedMember = memberRepository.save(member);

        // Then
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getMemberId()).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo("test@example.com");
        assertThat(savedMember.getPassword()).isEqualTo("password123");
    }

    @Test
    public void testFindByEmail() {
        // Given
        Member member = new Member("test@example.com", "password123");
        memberRepository.save(member);

        // When
        Optional<Member> foundMember = memberRepository.findByEmail("test@example.com");

        // Then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.get().getEmail()).isEqualTo("test@example.com");
        assertThat(foundMember.get().getPassword()).isEqualTo("password123");
    }

    @Test
    public void testDeleteMember() {
        // Given
        Member member = new Member("test@example.com", "password123");
        Member savedMember = memberRepository.save(member);

        // When
        memberRepository.delete(savedMember);
        Optional<Member> foundMember = memberRepository.findByEmail("test@example.com");

        // Then
        assertThat(foundMember).isNull();
    }
}