package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.Member;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private  MemberRepository memberRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Create Test")
    void save() {
        // given
        Member expected = new Member("donghyun","5434");

        // when
        Member actual = memberRepository.save(expected);

        // then
        assertAll(
            ()->assertThat(actual.getId()).isNotNull(),
            ()->assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
            ()->assertThat(actual.getPassword()).isEqualTo(expected.getPassword())
        );
    }

    @Test
    @DisplayName("Read By Email and Password")
    void findByEmailAndPassword() {
        // given
        String expectedEmail = "donghyun";
        String expectedPassword = "5434";
        Member expected = new Member(expectedEmail,expectedPassword);
        Member savedMember = memberRepository.save(expected);

        // when
        Member findMember = memberRepository.findByEmailAndPassword(savedMember.getEmail(),savedMember.getPassword()).get();

        // then
        assertAll(
            () -> assertThat(findMember.getId()).isNotNull(),
            () -> assertThat(findMember.getEmail()).isEqualTo(expectedEmail),
            ()->assertThat(findMember.getPassword()).isEqualTo(expectedPassword)
        );
    }

    @Test
    @DisplayName("Delete Test")
    void DeleteById(){
        // given
        String expectedEmail = "donghyun";
        String expectedPassword = "1234";
        Member expected = new Member(expectedEmail,expectedPassword);
        Member expectedMember = memberRepository.save(expected);
        Long savedId = expectedMember.getId();

        // when
        memberRepository.deleteById(expectedMember.getId());

        // then
        Optional<Member> deletedUser = memberRepository.findById(savedId);
        assertThat(deletedUser).isNotPresent();
    }
}