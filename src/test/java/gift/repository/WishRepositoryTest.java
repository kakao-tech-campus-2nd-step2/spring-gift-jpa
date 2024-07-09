package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Wish;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Test
    void save() {
        Wish expected = new Wish(1L, 1L, 1);

        Wish actual = wishRepository.save(expected);

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId()),
            () -> assertThat(actual.getProductId()).isEqualTo(expected.getProductId()),
            () -> assertThat(actual.getCount()).isEqualTo(expected.getCount())
        );
    }

    @Test
    void findAllByMemberId() {
        Wish expected = new Wish(1L, 1L, 1);
        Wish expected2 = new Wish(1L, 2L, 2);
        wishRepository.save(expected);
        wishRepository.save(expected2);

        List<Wish> actual = wishRepository.findAllByMemberId(expected.getMemberId());

        assertThat(actual).isEqualTo(List.of(expected, expected2));
    }

    @Test
    void findByMemberIdAndProductId() {
        Wish expected = new Wish(1L, 1L, 1);
        wishRepository.save(expected);

        Wish actual = wishRepository.findByMemberIdAndProductId(expected.getMemberId(),
            expected.getProductId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteByMemberIdAndProductId() {
        Wish expected = new Wish(1L, 1L, 1);
        wishRepository.save(expected);
        Wish actual = wishRepository.findByMemberIdAndProductId(expected.getMemberId(), expected.getProductId());
        assertThat(actual).isNotNull();

        wishRepository.deleteByMemberIdAndProductId(expected.getMemberId(), expected.getProductId());

        actual = wishRepository.findByMemberIdAndProductId(expected.getMemberId(), expected.getProductId());
        assertThat(actual).isNull();
    }
}
