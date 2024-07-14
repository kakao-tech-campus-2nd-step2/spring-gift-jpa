package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private Member invalidMember;

    @BeforeEach
    public void setUp() {
        member = new Member(1L, "kbm", "kbm@kbm.com", "mbk", "user");
    }


    @Test
    void testSave() {
        member.validate();
        Member savedMember = memberRepository.save(member);
        assertAll(
            () -> assertThat(savedMember.getId()).isNotNull(),
            () -> assertThat(savedMember.getName()).isEqualTo(member.getName()),
            () -> assertThat(savedMember.getEmail()).isEqualTo(member.getEmail()),
            () -> assertThat(savedMember.getPassword()).isEqualTo(member.getPassword()),
            () -> assertThat(savedMember.getRole()).isEqualTo(member.getRole())
        );
    }

    @Test
    void testFindByEmail() {
        member.validate();
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findByEmail(member.getEmail());
        assertAll(
            () -> assertThat(foundMember).isNotNull(),
            () -> assertThat(foundMember.getId()).isEqualTo(savedMember.getId())
        );
    }

    @Test
    void testSaveWithNullName() {
        try {
            invalidMember = new Member(1L, null, "kbm@kbm", "mbk", "user");
            invalidMember.validate();
            memberRepository.save(invalidMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithNullEmail() {
        try {
            invalidMember = new Member(1L, "kbm", null, "mbk", "user");
            invalidMember.validate();
            memberRepository.save(invalidMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithInvalidEmail() {
        try {
            invalidMember = new Member(1L, "kbm", "invalid", "mbk", "user");
            invalidMember.validate();
            memberRepository.save(invalidMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithNullPassword() {
        try {
            invalidMember = new Member(1L, "kbm", "kbm@kbm", null, "user");
            invalidMember.validate();
            memberRepository.save(invalidMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }
}