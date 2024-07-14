package gift.domain.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository members;

    @Test
    void save() {
        Member expected = new Member("wjdghtjd06@gmail.com", "1234");
        Member actual = members.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull().isEqualTo(1),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
            () -> assertThat(actual.getRole()).isEqualTo(expected.getRole())
        );
    }

    @Test
    void findByEmail() {
        String expected = "wjdghtjd06@gmail.com";
        members.save(new Member("wjdghtjd06@gmail.com", "1234"));
        String actual = members.findByEmail(expected).orElseThrow().getEmail();
        assertThat(actual).isEqualTo(expected);
    }


}