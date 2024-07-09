package gift.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import gift.model.Product;
import gift.model.User;
import gift.model.Wishlist;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishlistRepository;

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
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("pw");
        userRepository.save(user);

        Product product = new Product();
        product.setName("아이스 아메리카노 T");
        product.setPrice(4500);
        product.setImageUrl("https://example.com/image.jpg");
        productRepository.save(product);

        Wishlist expected = new Wishlist();
        expected.setUser(user);
        expected.setProduct(product);
        expected.setQuantity(2);
        Wishlist actual = wishlistRepository.save(expected);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getUser()).isEqualTo(expected.getUser());
        assertThat(actual.getProduct()).isEqualTo(expected.getProduct());
        assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity());
    }

    @Test
    void findByUserId() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("pw");
        userRepository.save(user);

        Product product = new Product();
        product.setName("아이스 아메리카노 T");
        product.setPrice(4500);
        product.setImageUrl("https://example.com/image.jpg");
        productRepository.save(product);

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlist.setQuantity(2);
        wishlistRepository.save(wishlist);

        List<Wishlist> wishlistItems = wishlistRepository.findByUserId(user.getId());
        assertThat(wishlistItems).hasSize(1);
        assertThat(wishlistItems.get(0).getProduct().getName()).isEqualTo(product.getName());
    }
}
