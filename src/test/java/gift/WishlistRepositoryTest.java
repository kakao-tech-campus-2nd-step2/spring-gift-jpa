package gift;

import gift.Model.Product;
import gift.Model.Wishlist;
import gift.Repository.ProductRepository;
import gift.Repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class WishlistRepositoryTest {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void save(){
        Wishlist expected = new Wishlist(1L, 1L);
        Wishlist actual = wishlistRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId())
        );
    }

    @Test
    void findByName() {
        Long expectedMemberId = 1L;
        Long expectedProductId = 1L;
        wishlistRepository.save(new Wishlist(expectedMemberId, expectedProductId));
        Long actual = wishlistRepository.findByMemberId(expectedMemberId).get(0).getMemberId();
        assertThat(actual).isEqualTo(expectedMemberId);
    }

    @Test
    void deleteByProductIDAndMemberId(){
        Long expectedMemberId = 1L;
        Long expectedProductId = 1L;
        wishlistRepository.save(new Wishlist(expectedMemberId, expectedProductId));
        Long actual = wishlistRepository.findByMemberIdAndProductId(expectedMemberId, expectedProductId).getMemberId();
        assertThat(actual).isEqualTo(expectedMemberId);
    }
}
