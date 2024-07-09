package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Product;
import gift.model.User;
import gift.model.Wishlist;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        User user = new User(4L, "kbm", "kbm@kbm", "mbk", "user");
        User savedUser = userRepository.save(user);
        Product product = new Product(1L, "상품", "100", "https://kakao");
        Product savedProduct = productRepository.save(product);
    }

    @Test
    public void testSaveWishlist() {

        Wishlist wishlist = new Wishlist(1L, "kbm@kbm", 1L);

        Wishlist saved = wishlistRepository.save(wishlist);

        assertAll(
            () -> assertThat(saved.getId()).isNotNull(),
            () -> assertThat(saved.getUserEmail()).isEqualTo("kbm@kbm"),
            () -> assertThat(saved.getProductId()).isEqualTo(1L)
        );
    }

    @Test
    public void testFindByUserEmail() {
        Wishlist wishlist = new Wishlist(1L, "kbm@kbm", 1L);
        wishlistRepository.save(wishlist);
        List<Wishlist> found = wishlistRepository.findByUserEmail("kbm@kbm");
        assertAll(
            () -> assertThat(found.size()).isEqualTo(1),
            () -> assertThat(found.get(0).getUserEmail()).isEqualTo("kbm@kbm")
        );
    }

    @Test
    void testDeleteByUserEmailAndProductId() {
        Wishlist wishlist = new Wishlist(1L, "kbm@kbm", 1L);
        wishlistRepository.save(wishlist);
        wishlistRepository.deleteByUserEmailAndProductId("kbm@kbm", 1L);
        List<Wishlist> result = wishlistRepository.findByUserEmail("kbm@kbm");
        assertThat(result.size()).isEqualTo(0);
    }
}