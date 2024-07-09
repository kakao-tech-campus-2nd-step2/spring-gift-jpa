package gift.test;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Product;
import gift.model.SiteUser;
import gift.model.Wishlist;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        SiteUser user = new SiteUser();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        userRepository.save(user);

        Product product = new Product();
        product.setName("product1");
        product.setPrice(1000);
        product.setImageUrl("http://example.com/image.jpg");
        productRepository.save(product);

        Wishlist wishlist = new Wishlist();
        wishlist.setUsername(user.getUsername());
        wishlist.setProductId(product.getId());
        wishlist.setQuantity(2);
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        assertThat(savedWishlist.getId()).isNotNull();
        assertThat(savedWishlist.getUsername()).isEqualTo(user.getUsername());
        assertThat(savedWishlist.getProductId()).isEqualTo(product.getId());
    }
}
