package gift.repository;

import gift.config.SpringConfig;
import gift.model.Product;
import gift.model.ProductDTO;
import gift.model.WishList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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
        ProductDTO product1 = new ProductDTO("test1", 123, "test1.com");
        ProductDTO product2 = new ProductDTO("test2", 456, "test2.com");
        Product saved1 = productRepository.save(product1);
        Product saved2 = productRepository.save(product2);

        // when
        WishList wishlist = wishlistRepository.findByEmail(testEmail);
        wishlist.addProduct(saved1);
        wishlist.addProduct(saved2);
        boolean expect = wishlistRepository.save(wishlist);

        // then
        assertThat(expect).isTrue();
    }

    @Test
    public void find() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product1 = new ProductDTO("test1", 123, "test1.com");
        ProductDTO product2 = new ProductDTO("test2", 456, "test2.com");
        Product saved1 = productRepository.save(product1);
        Product saved2 = productRepository.save(product2);

        // when
        WishList wishlist = wishlistRepository.findByEmail(testEmail);
        wishlist.addProduct(saved1);
        wishlist.addProduct(saved2);
        wishlistRepository.save(wishlist);

        // then
        WishList expect = wishlistRepository.findByEmail(testEmail);
        assertThat(expect.getProducts().size()).isEqualTo(2);
    }

    @Test
    public void delete() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product = new ProductDTO("test", 123, "test.com");
        Product saved = productRepository.save(product);

        // when
        WishList wishlist = wishlistRepository.findByEmail(testEmail);
        wishlist.addProduct(saved);
        wishlistRepository.save(wishlist);
        boolean result = wishlistRepository.delete(testEmail);
        WishList expect = wishlistRepository.findByEmail(testEmail);

        // then
        Assertions.assertAll(
                () -> assertThat(result).isTrue(),
                () -> assertThat(expect.getProducts().size()).isEqualTo(0)

        );
    }

    @Test
    public void deleteProductCascade() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product1 = new ProductDTO("test1", 123, "test1.com");
        ProductDTO product2 = new ProductDTO("test2", 456, "test2.com");
        Product saved1 = productRepository.save(product1);
        Product saved2 = productRepository.save(product2);

        // when
        WishList wishlist = wishlistRepository.findByEmail(testEmail);
        wishlist.addProduct(saved1);
        wishlist.addProduct(saved2);
        wishlistRepository.save(wishlist);

        productRepository.delete(saved1.getId());

        WishList expect = wishlistRepository.findByEmail(testEmail);

        // then
        assertThat(expect.getProducts().size()).isEqualTo(1);
    }
}
