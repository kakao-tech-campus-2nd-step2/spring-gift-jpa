package gift.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
    
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
    	 user = new User("tset@test.com", "pw");
    	 userRepository.save(user);
    	 product = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
    	 productRepository.save(product);
    }
    
    @Test
    void save() {
    	Wishlist expected = new Wishlist(user, product);
    	expected.setQuantity(2);
    	Wishlist actual = wishlistRepository.save(expected);

    	assertThat(actual.getId()).isNotNull();
    	assertThat(actual.getUser()).isEqualTo(expected.getUser());
    	assertThat(actual.getProduct()).isEqualTo(expected.getProduct());
    	assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity());
    }

    @Test
    void findByUserId() {
    	Wishlist wishlist = new Wishlist(user, product);
    	wishlist.setQuantity(2);
    	wishlistRepository.save(wishlist);

    	List<Wishlist> WishlistItems = wishlistRepository.findByUserId(user.getId());
    	assertThat(WishlistItems).hasSize(1);
    	assertThat(WishlistItems.get(0).getProduct().getName()).isEqualTo(product.getName());
    }
}
