package gift.repository;

import gift.model.Product;
import gift.model.ProductDTO;
import gift.model.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JpaWishlistRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishlistRepository wishlistRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void save() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product1 = new ProductDTO("test1", 123, "test1.com");
        ProductDTO product2 = new ProductDTO("test2", 456, "test2.com");
        Product saved1 = productRepository.save(new Product(product1));
        Product saved2 = productRepository.save(new Product(product2));

        Wishlist wishlist = wishlistRepository.save(new Wishlist(testEmail));

        // when
        wishlist.addProduct(saved1);
        wishlist.addProduct(saved2);
        Wishlist expect = wishlistRepository.save(wishlist);

        // then
        assertThat(expect.getProducts().size()).isEqualTo(2);
    }

    @Test
    public void find() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product1 = new ProductDTO("test1", 123, "test1.com");
        ProductDTO product2 = new ProductDTO("test2", 456, "test2.com");
        Product saved1 = productRepository.save(new Product(product1));
        Product saved2 = productRepository.save(new Product(product2));

        Wishlist wishlist = wishlistRepository.save(new Wishlist(testEmail));

        // when
        wishlist.addProduct(saved1);
        wishlist.addProduct(saved2);
        wishlistRepository.save(wishlist);

        Wishlist expect = wishlistRepository.findByEmail(testEmail).get();

        // then
        assertThat(expect.getProducts().size()).isEqualTo(2);
    }

    @Test
    public void delete() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product = new ProductDTO("test", 123, "test.com");
        Product saved = productRepository.save(new Product(product));
        Wishlist wishlist = new Wishlist(testEmail);
        wishlist.addProduct(saved);
        wishlistRepository.save(wishlist);

        // when
        Wishlist expect = wishlistRepository.findByEmail(testEmail).get();
        wishlistRepository.delete(wishlist);

        // then
        assertThat(wishlistRepository.findByEmail(testEmail).isPresent()).isFalse();
    }

    @Test
    public void deleteProductCascade() {
        // given
        String testEmail = "test@naver.com";
        ProductDTO product1 = new ProductDTO("test1", 123, "test1.com");
        ProductDTO product2 = new ProductDTO("test2", 456, "test2.com");
        Product saved1 = productRepository.save(new Product(product1));
        Product saved2 = productRepository.save(new Product(product2));

        // when
        Wishlist wishlist = new Wishlist(testEmail);
        wishlist.addProduct(saved1);
        wishlist.addProduct(saved2);
        wishlistRepository.save(wishlist);

        productRepository.delete(saved1);

        Wishlist expect = wishlistRepository.findByEmail(testEmail).get();

        // then
        assertThat(expect.getProducts().size()).isEqualTo(1);
    }
}
