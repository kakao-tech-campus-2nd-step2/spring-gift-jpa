package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.member.Member;
import gift.model.member.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    public void save() {
        // given
        Member member = new Member(1L, "member1@asd.com", "asd", "asd", Role.USER);
        // when
        memberJpaRepository.save(member);
        // then
        assertAll(
            () -> assertThat(memberJpaRepository.findById(1L).get().getEmail()).isEqualTo(
                "member1@asd.com"),
            () -> assertThat(memberJpaRepository.findById(1L).get().getPassword()).isEqualTo("asd"),
            () -> assertThat(memberJpaRepository.findById(1L).get().getName()).isEqualTo("asd"),
            () -> assertThat(memberJpaRepository.findById(1L).get().getRole()).isEqualTo(Role.USER)
        );
    }

    @Test
    public void delete() {
        // given
        Member member = new Member(1L, "member1@asd.com", "asd", "asd", Role.USER);
        memberJpaRepository.save(member);
        // when
        memberJpaRepository.deleteById(1L);
        // then
        assertThat(memberJpaRepository.findById(1L)).isEmpty();
    }

    @Test
    public void findByEmail() {
        // given
        Member member = new Member(1L, "member1@asd.com", "asd", "asd", Role.USER);
        memberJpaRepository.save(member);
        // when
        Member findMember = memberJpaRepository.findByEmail("member1@asd.com").get();
        // then
        assertAll(
            () -> assertThat(findMember.getEmail()).isEqualTo("member1@asd.com"),
            () -> assertThat(findMember.getPassword()).isEqualTo("asd"),
            () -> assertThat(findMember.getName()).isEqualTo("asd")
        );
    }
}
