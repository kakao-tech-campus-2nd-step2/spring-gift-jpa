package gift.test;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Product;
import gift.model.SiteUser;
import gift.model.Wishlist;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;


    private Wishlist wishlist;
    private SiteUser siteUser;
    private Product product;

    @BeforeEach
    public void setUp() {
        siteUser = new SiteUser();
        siteUser.setUsername("testuser");
        siteUser.setPassword("testpass");
        siteUser.setEmail("testuser@example.com");
        userRepository.save(siteUser);

        product = new Product();
        product.setName("Test Product");
        product.setPrice(100);
        product.setImageUrl("http://example.com/image.jpg");
        productRepository.save(product);

        wishlist = new Wishlist();
        wishlist.setUser(siteUser);
        wishlist.setProduct(product);
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
        assertThat(savedWishlist.getUser()).isEqualTo(siteUser);
        assertThat(savedWishlist.getProduct()).isEqualTo(product);
        assertThat(savedWishlist.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("위시리스트에 상품 조회하기")
    public void testFindByUsername() {
        // given
        wishlistRepository.save(wishlist);

        // when
        List<Wishlist> foundWishlist = wishlistRepository.findByUserUsername("testuser");

        // then
        assertThat(foundWishlist).hasSize(1);
        Wishlist foundItem = foundWishlist.get(0);
        assertThat(foundItem.getUser().getUsername()).isEqualTo("testuser");
        assertThat(foundItem.getProduct()).isEqualTo(product);
        assertThat(foundItem.getQuantity()).isEqualTo(2);
    }
}
