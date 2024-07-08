package gift.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.member.model.Member;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@Import(MemberRepository.class)
class MemberRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testFindByEmail() {
        Member member = new Member();
        member.setEmail("user@example.com");
        member.setPassword("password");
        memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findByEmail("user@example.com");
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo("user@example.com");
    }

    @Test
    void testFindByEmail_NotFound() {
        Optional<Member> foundMember = memberRepository.findByEmail("noUser@example.com");
        assertThat(foundMember).isNotPresent();
    }

    @Test
    void testSave() {
        Member newMember = new Member();
        newMember.setEmail("newUser@example.com");
        newMember.setPassword("newPassword");
        memberRepository.save(newMember);
        assertThat(newMember.getEmail()).isEqualTo("newUser@example.com");
    }
}