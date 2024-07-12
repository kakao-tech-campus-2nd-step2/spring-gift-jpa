package gift;


import gift.domain.Member;
import gift.domain.Menu;
import gift.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.LinkedList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberTest {
    @Autowired
    private MemberRepository members;

    @Test
    void save() {
        Member expected = new Member("alswl4223@naver.com","dfsd",new LinkedList<>());
        Member actual = members.save(expected);
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected)
        );
    }


}
