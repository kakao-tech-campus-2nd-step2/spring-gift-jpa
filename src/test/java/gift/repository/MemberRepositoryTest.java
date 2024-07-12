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

    private MemberRepository memberRepository;

    private Member member;

    @Autowired
    public MemberRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @BeforeEach
    public void setUp() {
        member = new Member(1L, "kbm", "kbm@kbm", "mbk", "user");
    }


    @Test
    void testSave() {
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
        Member savedMember = memberRepository.save(member);
        assertAll(
            () -> assertThat(savedMember).isNotNull(),
            () -> assertThat(savedMember.getId()).isEqualTo(member.getId())
        );
    }

}