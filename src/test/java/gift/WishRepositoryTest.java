package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import gift.product.model.Wish;
import gift.product.repository.WishRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {
    @Autowired
    WishRepository wishRepository;

    @Test
    void 위시리스트_항목_추가() {
        Wish wish = new Wish(1L, 1L);
        Wish insertedWish = wishRepository.save(wish);

        assertSoftly(softly -> {
            assertThat(insertedWish.getId()).isNotNull();
            assertThat(insertedWish.getMemberId()).isEqualTo(1L);
            assertThat(insertedWish.getProductId()).isEqualTo(1L);
        });
    }

    @Test
    void 위시리스트_전체_조회() {
        wishRepository.save(new Wish(1L, 1L));
        wishRepository.save(new Wish(1L, 2L));

        List<Wish> wishes = wishRepository.findAllByMemberId(1L);

        assertThat(wishes).hasSize(2);
    }

    @Test
    void 위시리스트_조회() {
        wishRepository.save(new Wish(1L, 1L));

        boolean isPresentWish = wishRepository.findByIdAndMemberId(1L, 1L).isPresent();

        assertThat(isPresentWish).isTrue();
    }

    @Test
    void 위시리스트_항목_삭제() {
        wishRepository.save(new Wish(1L, 1L));
        wishRepository.deleteById(1L);
        boolean isPresentWish = wishRepository.findByIdAndMemberId(1L, 1L).isPresent();

        assertThat(isPresentWish).isFalse();
    }
}
