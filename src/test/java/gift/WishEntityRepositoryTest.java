package gift;

import gift.Model.Wish;
import gift.Repository.WishRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;

    @Test
    void save(){
        Wish expected = new Wish(1L, 1L);
        Wish actual = wishRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId())
        );
    }

    @Test
    void findByName() {
        Long expectedMemberId = 1L;
        Long expectedProductId = 1L;
        wishRepository.save(new Wish(expectedMemberId, expectedProductId));
        Long actual = wishRepository.findByMemberId(expectedMemberId).get(0).getMemberId();
        assertThat(actual).isEqualTo(expectedMemberId);
    }

    @Test
    void deleteByProductIDAndMemberId(){
        Long expectedMemberId = 1L;
        Long expectedProductId = 1L;
        wishRepository.save(new Wish(expectedMemberId, expectedProductId));
        Long actual = wishRepository.findByMemberIdAndProductId(expectedMemberId, expectedProductId).getMemberId();
        assertThat(actual).isEqualTo(expectedMemberId);
    }
}
