package gift.repository;

import gift.domain.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private  MemberRepository members;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("멤버 저장 테스트")
    void save() {
        // given
        Member expected = new Member("a@a.com","1234");

        // when
        Member actual = members.save(expected);

        // then
        assertAll(
                ()->assertThat(actual.getId()).isNotNull(),
                ()->assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                ()->assertThat(actual.getPassword()).isEqualTo(expected.getPassword())
        );
    }

    @Test
    @DisplayName("멤버 이메일, 비밀번호로 조회 테스트")
    void findByEmailAndPassword() {
        // given
        String expectedEmail = "a@a.com";
        String expectedPassword = "1234";
        Member expected = new Member(expectedEmail,expectedPassword);
        Member savedMember = members.save(expected);
        entityManager.flush();
        entityManager.clear();

        // when
        Member findMember = members.findByEmailAndPassword(savedMember.getEmail(),savedMember.getPassword()).get();

        // then
        assertAll(
                () -> assertThat(findMember.getId()).isNotNull(),
                () -> assertThat(findMember.getEmail()).isEqualTo(expectedEmail),
                ()->assertThat(findMember.getPassword()).isEqualTo(expectedPassword)
        );
    }

    @Test
    @DisplayName("멤버 이메일 조회 테스트")
    void findByEmail() {
        // given
        String expectedEmail = "a@a.com";
        String expectedPassword = "1234";
        Member expected = new Member(expectedEmail,expectedPassword);
        Member savedMember = members.save(expected);
        entityManager.flush();
        entityManager.clear();

        // when
        Member findMember = members.findByEmail(savedMember.getEmail()).get();

        // then
        assertAll(
                () -> assertThat(findMember.getId()).isNotNull(),
                () -> assertThat(findMember.getEmail()).isEqualTo(expectedEmail),
                ()->assertThat(findMember.getPassword()).isEqualTo(expectedPassword)
        );
    }
}