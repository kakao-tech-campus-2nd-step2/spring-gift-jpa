package gift.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.member.model.Member;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setEmail("user@example.com");
        member.setPassword("password");
        memberRepository.save(member);
    }

    @Test
    void testFindByEmail() {
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
        Member savedMember = memberRepository.save(newMember);
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo("newUser@example.com");
    }
}