package gift.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        Member expected = new Member("westzeroright","errorai");
        Member actual = memberRepository.save(expected);
        assertAll(
            () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @Test
    void find() {
        Member expected = new Member("westzeroright", "errorai");
        memberRepository.save(expected);
        Member actual = memberRepository.findById(expected.getId()).get();

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getEmail()).isEqualTo("westzeroright"),
            () -> assertThat(actual.getPassword()).isEqualTo("errorai")
        );

    }

    @Test
    void findByEmail() {
        Member expected = new Member("westzeroright", "errorai");
        memberRepository.save(expected);
        Member actual = memberRepository.findByEmail(expected.getEmail());

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getEmail()).isEqualTo("westzeroright"),
            () -> assertThat(actual.getPassword()).isEqualTo("errorai")
        );
    }
}
