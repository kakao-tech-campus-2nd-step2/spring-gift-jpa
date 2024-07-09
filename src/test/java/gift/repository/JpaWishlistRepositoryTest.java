package gift.repository;

import gift.config.SpringConfig;
import gift.model.*;
import gift.util.UserUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(SpringConfig.class)
public class JpaWishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void save() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product = new ProductDTO("test", 123, "test.com");
        Product saved = productRepository.save(product);
        WishListDTO actual = new WishListDTO(saved.getId(), 10);

        // when
        boolean result = wishlistRepository.addWishlist(testEmail, actual);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void find() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product = new ProductDTO("test", 123, "test.com");
        Product saved = productRepository.save(product);
        WishListDTO actual = new WishListDTO(saved.getId(), 10);

        // when
        wishlistRepository.addWishlist(testEmail, actual);
        WishList expect = wishlistRepository.getMyWishlists(testEmail).getFirst();

        // then
        Assertions.assertAll(
                () -> assertThat(expect.getId()).isNotNull(),
                () -> assertThat(expect.getEmail()).isEqualTo(testEmail),
                () -> assertThat(expect.getProductId()).isEqualTo(actual.getProductId()),
                () -> assertThat(expect.getCount()).isEqualTo(actual.getCount())
        );
    }

    @Test
    public void edit() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product = new ProductDTO("test", 123, "test.com");
        Product saved = productRepository.save(product);
        WishListDTO wishlist = new WishListDTO(saved.getId(), 10);
        wishlistRepository.addWishlist(testEmail, wishlist);

        // when
        WishListDTO actual = new WishListDTO(saved.getId(), 100);
        wishlistRepository.updateWishlist(testEmail, actual);
        WishList expect = wishlistRepository.getMyWishlists(testEmail).getFirst();

        // then
        Assertions.assertAll(
                () -> assertThat(expect.getId()).isNotNull(),
                () -> assertThat(expect.getEmail()).isEqualTo(testEmail),
                () -> assertThat(expect.getProductId()).isEqualTo(actual.getProductId()),
                () -> assertThat(expect.getCount()).isEqualTo(actual.getCount())
        );
    }

    @Test
    public void delete() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product = new ProductDTO("test", 123, "test.com");
        Product saved = productRepository.save(product);
        WishListDTO actual = new WishListDTO(saved.getId(), 10);

        // when
        wishlistRepository.addWishlist(testEmail, actual);
        boolean result = wishlistRepository.removeWishlist(testEmail, saved.getId());
        List<WishList> myWishlists = wishlistRepository.getMyWishlists(testEmail);

        // then
        Assertions.assertAll(
                () -> assertThat(result).isTrue(),
                () -> assertThat(myWishlists).hasSize(0)

        );
    }
}
