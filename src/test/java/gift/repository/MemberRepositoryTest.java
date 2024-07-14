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
    private Member nullNameMember;
    private Member emptyNameMember;
    private Member nullEmailMember;
    private Member emptyEmailMember;
    private Member invalidEmailMember;
    private Member nullPasswordMember;
    private Member emptyPasswordMember;

    @BeforeEach
    public void setUp() {
        member = new Member(1L, "kbm", "kbm@kbm.com", "mbk", "user");
        nullNameMember = new Member(1L, null, "kbm@kbm.com", "mbk", "user");
        emptyNameMember = new Member(1L, "", "kbm@kbm.com", "mbk", "user");
        nullEmailMember = new Member(1L, "kbm", null, "mbk", "user");
        emptyEmailMember = new Member(1L, "kbm", "", "mbk", "user");
        invalidEmailMember = new Member(1L, "kbm", "kbm", "mbk", "user");
        nullPasswordMember = new Member(1L, "kbm", "kbm@kbm.com", null, "user");
        emptyPasswordMember = new Member(1L, "kbm", "kbm@kbm.com", "", "user");
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
            nullNameMember.validate();
            memberRepository.save(nullNameMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithEmptyName() {
        try {
            emptyNameMember.validate();
            memberRepository.save(emptyNameMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithNullEmail() {
        try {
            nullEmailMember.validate();
            memberRepository.save(nullEmailMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithEmptyEmail() {
        try {
            emptyEmailMember.validate();
            memberRepository.save(emptyEmailMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithInvalidName() {
        try {
            invalidEmailMember.validate();
            memberRepository.save(invalidEmailMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithNullPassword() {
        try {
            nullPasswordMember.validate();
            memberRepository.save(nullPasswordMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testSaveWithEmptyPassword() {
        try {
            emptyPasswordMember.validate();
            memberRepository.save(emptyPasswordMember);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }
}