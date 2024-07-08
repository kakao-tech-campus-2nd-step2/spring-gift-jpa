package gift.test;

import static org.assertj.core.api.Assertions.assertThat;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Transactional
public class WishlistRepositoryTest {
    @Autowired
    private WishlistRepository wishlistRepository;

    private Wishlist wishlist;

    @BeforeEach
    public void setUp() {
        wishlist = new Wishlist();
        wishlist.setUsername("testuser");
        wishlist.setProductId(1L);
        wishlist.setQuantity(2);
    }

    @Test
    @DisplayName("위시리스트에 상품 추가하기")
    public void testSaveWishlist() {
        // when
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        // then
        assertThat(savedWishlist).isNotNull();
        assertThat(savedWishlist.getId()).isNotNull();
        assertThat(savedWishlist.getUsername()).isEqualTo("testuser");
        assertThat(savedWishlist.getProductId()).isEqualTo(1L);
        assertThat(savedWishlist.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("위시리스트에 상품 조회하기")
    public void testFindByUsername() {
        // given
        wishlistRepository.save(wishlist);

        // when
        List<Wishlist> foundWishlist = wishlistRepository.findByUsername("testuser");

        // then
        assertThat(foundWishlist).hasSize(1);
        Wishlist foundItem = foundWishlist.get(0);
        assertThat(foundItem.getUsername()).isEqualTo("testuser");
        assertThat(foundItem.getProductId()).isEqualTo(1L);
        assertThat(foundItem.getQuantity()).isEqualTo(2);
    }
}
