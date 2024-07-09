package gift.api.wishlist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Test
    void deleteByMemberIdAndProductId() {
        Long memberId = 1L;
        Long productId = 1L;
        Wish wish = new Wish(memberId, new WishRequest(productId, 5));
        wishRepository.save(wish);
        wishRepository.deleteByMemberIdAndProductId(memberId, productId);

        assertThat(wishRepository.findById(new WishId(memberId, productId))).isEmpty();
    }
}