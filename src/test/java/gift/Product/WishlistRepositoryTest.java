package gift.Product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.model.WishList;
import gift.repository.WishlistRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishlistRepositoryTest {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void save() {
        WishList expected = new WishList(111L, 111L);
        WishList actual = wishlistRepository.save(expected);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId());
        assertThat(actual.getProductId()).isEqualTo(expected.getProductId());
    }

    @Test
    void findById() {
        WishList expected = new WishList(100L, 100L);
        WishList actual = wishlistRepository.save(expected);

        assertThat(actual.getMemberId()).isEqualTo(100L);
    }

    @Test
    void deleteById() {
        WishList expected = new WishList(100L, 100L);
        wishlistRepository.save(expected);
        wishlistRepository.deleteById(expected.getProductId());
        Optional<WishList> deletedWish = wishlistRepository.findById(expected.getMemberId());
        assertThat(deletedWish).isNotPresent();

    }
}
