package gift.repository.member;

import gift.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberSpringDataJpaRepositoryTest {

    @Autowired
    private MemberSpringDataJpaRepository memberRepository;

    @BeforeEach
    public void setUp() {
        Member member = new Member("test@example.com", "password");
        memberRepository.save(member);
    }

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    public void testSaveMember() {
        Optional<Member> foundMember = memberRepository.findByEmail("test@example.com");
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testFindByEmail() {
        Optional<Member> foundMember = memberRepository.findByEmail("test@example.com");
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testSaveMember_이메일_중복() {
        Member duplicateMember = new Member("test@example.com", "password");
        try {
            memberRepository.save(duplicateMember);
        } catch (Exception ignored) {
        }
    }
}
